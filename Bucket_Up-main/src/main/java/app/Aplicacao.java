package main.java.app;

import org.json.JSONObject;
import org.json.*;
import org.sql2o.Sql2o;

import main.java.service.EquipamentoService;
import main.java.service.LogService;
import main.java.service.UsuarioService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static spark.Spark.*;


public class Aplicacao {
    public static <ObjectMapper, CloseableHttpClient> void main(String[] arg) throws InterruptedException, IOException {

        UsuarioService usuarioService = new UsuarioService();
        EquipamentoService quipamentoService = new EquipamentoService();
        LogService ogService = new LogService();

        port(8081);

        before((request, response) -> response.type("application/json"));
        //Habilita o CORS, para que possa ser feita uma request de uma origem diferente
        options("/*",
                    (request, response) -> {

                        String accessControlRequestHeaders = request
                                .headers("Access-Control-Request-Headers");
                        if (accessControlRequestHeaders != null) {
                            response.header("Access-Control-Allow-Headers",
                                    accessControlRequestHeaders);
                        }

                        String accessControlRequestMethod = request
                                .headers("Access-Control-Request-Method");
                        if (accessControlRequestMethod != null) {
                            response.header("Access-Control-Allow-Methods",
                                    accessControlRequestMethod);
                        }

                        return "OK";
                    });

            before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        //Operações relacionadas ao usuario
        post("/operate_user/create_user",(request,response) -> usuarioService.add(request,response));
        post("/operate_user/update_usuario",(request,response) -> usuarioService.update(request,response));
        post("/operate_user/check_exist",(request,response) -> usuarioService.checkU(request,response));
        post("/operate_use/check_login",(request,response) -> usuarioService.checkL(request,response));

        //Requisições relacionadas com o equipamento
        post("/operate_equipment/create_equipment",(request,response) -> equipamentoService.add(request,response));
        post("/operate_equipment/update_equipment",(request,response) -> equipamentoService.update(request,response));
        post("/operate_equipment/delete_equipment",(request,response) -> equipamentoService.delete(request,response));
        post("/operate_equipment/listar_equipment",(request,response) -> equipamentoService.listar(request,response));

        //Operações destinadas a parte de log
        post("/operate_log/listar_log",(request,response) -> logService.listar(request,response));
        post("/operate_log/create_log",(request,response) -> logService.add(request,response));

        //Operações relacionadas a criação dos arquivos
        post("/operate_arquivo/create_arquivo",(request,response) -> logService.addArquivo(request,response));

        schedule_handle();

    }
    //Cria uma classe para receber os dados da API
    private class receive_http{
        String status;
    }
    public static void schedule_handle() throws InterruptedException, IOException {
        while (true){
            Thread.sleep(60000); // 60 segundos
            URL url = new URL("http://127.0.0.1:8081/operate_equipment/listar_equipment?id_user=all");

            //System.out.println(url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            BufferedReader br = null;
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String saida = br.readLine();
            System.out.println(saida);
            JSONArray jsonArray = new JSONArray(saida);
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject line = (JSONObject) jsonArray.get(i);
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                String now_hour = formatter.format(date).split(":")[0];
                String now_minut = formatter.format(date).split(":")[1];
                String equipment_hour = line.getString("agendamento").split(":")[0];
                String equipment_minute = line.getString("agendamento").split(":")[1];
                if (equipment_hour.equals(now_hour) && equipment_minute.equals(now_minut)){
                    System.out.println("igual");
                    URL url_exec = new URL("http://127.0.0.1:8081/operate_arquivo/create_arquivo?" +
                            "action=generate&" +
                            "ip="+line.getString("ip")+"&" +
                            "usuario_login="+line.getString("usuario_login")+"&" +
                            "senha_login="+line.getString("senha_login")+"&" +
                            "pastas="+line.getString("pastas")+"&" +
                            "id="+Integer.toString(line.getInt("id"))+"&" +
                            "port="+line.getString("port")+"" +
                            "hostname="+line.getString("hostname")+"" +
                            "");
                    System.out.println(url_exec);
                    HttpURLConnection con_exec = (HttpURLConnection) url_exec.openConnection();
                    con_exec.setRequestMethod("POST");
                    int status = con_exec.getResponseCode();
                }
                //System.out.println();
            }
        }
    }
}

