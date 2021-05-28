package main.java.service;


import dao.LogDAO;
import model.Log;
import org.json.JSONObject;
import org.json.*;
import org.sql2o.Sql2o;

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

public class LogService {

	private LogDAO LogDAO;

	public LogService() {
		
			LogDAO = new LogDAO();
			LogDAO.Conectar();
	}

    public Object listar(Request request, Response response)
    {
        String id_equipment;

        try{
            id_equipment = request.queryParams("id_equipment");


        }catch  (Exception e){
            return "erro";
        }

        return LogDAO.listar_log(id_equipment);
    }

    public Object add(Request request, Response response)
    {
            
        String id_equipment;
        String nome_arquivo;

        try{
            id_equipment = request.queryParams("id_equipment");
            nome_arquivo = request.queryParams("nome_arquivo");


        }catch  (Exception e){
            return "erro";
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Log log = new Log(id_equipment,now,nome_arquivo);

        return Log.create_log_event(log);
    }

    public Object addArquivo(Request request, Response response)
    {
        String ip;
            String usuario;
            String senha;
            String porta;
            String pasta;
            String nome_arquivo;
            String id_equipment;
            String hostname;
            try{
                ip = request.queryParams("ip");
                usuario = request.queryParams("usuario_login");
                senha = request.queryParams("senha_login");
                pasta = request.queryParams("pastas");
                porta = request.queryParams("port");
                id_equipment = request.queryParams("id");
                hostname = request.queryParams("hostname");
            }catch  (Exception e){
                return "erro";
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            nome_arquivo=id_equipment+hostname+now;
            nome_arquivo.replace(":","_");

            //Realiza a chamada a aplicação que fara a obtenção do arquivo

            URL url = new URL("http://192.168.80.130:8765?" +
                    "action=generate&" +
                    "ip="+ip+"&" +
                    "usuario="+usuario+"&" +
                    "senha="+senha+"&" +
                    "pasta="+pasta+"&" +
                    "nome_arquivo="+nome_arquivo+"&" +
                    "porta="+porta+"" +
                    "");
            System.out.println(url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            int status = con.getResponseCode();
            System.out.println(status);

            Log log = new Log(id_equipment,now,nome_arquivo);

            return LogDAO.create_log_event(log);
    }

}