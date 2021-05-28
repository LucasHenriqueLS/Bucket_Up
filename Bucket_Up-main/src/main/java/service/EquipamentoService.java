package main.java.service;


import dao.EquipamentoDAO;
import model.Equipamento;
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


public class EquipamentoService {

	private EquipamentoDAO EquipamentoDAO;

	public EquipamentoService() {
		
        EquipamentoDAO = new EquipamentoDAO();
        EquipamentoDAO.Conectar();
	}

    public Object add(Request request, Response response)
    {
        String  hostname;
        String  usuario_login;
        String  senha_login;
        String  ip;
        String  port;
        String id_user;
        String pastas;
        String agendamento;

        try{
            hostname = request.queryParams("hostname");
            usuario_login = request.queryParams("usuario_login");
            senha_login = request.queryParams("senha_login");
            ip = request.queryParams("ip");
            port = request.queryParams("port");
            id_user = request.queryParams("id_user");
            pastas = request.queryParams("pastas");
            agendamento = request.queryParams("agendamento");

        }catch  (Exception e){
            return "erro";
        }

        Equipamento equipamento = new Equipamento( hostname,  usuario_login,  senha_login,  ip,  port,
         pastas,  agendamento,  id_user);

        return EquipamentoDAO.create_equipment(equipamento);
    }

    public Object update(Request request, Response response)
    {
        String  hostname;
        String  usuario_login;
        String  senha_login;
        String  ip;
        String  port;
        String id_user;
        String pastas;
        String agendamento;

        try{
            hostname = request.queryParams("hostname");
            usuario_login = request.queryParams("usuario_login");
            senha_login = request.queryParams("senha_login");
            ip = request.queryParams("ip");
            port = request.queryParams("port");
            id_user = request.queryParams("id_user");
            pastas = request.queryParams("pastas");
            agendamento = request.queryParams("agendamento");

        }catch  (Exception e){
            return "erro";
        }

        Equipamento equipamento = new Equipamento( hostname,  usuario_login,  senha_login,  ip,  port,
         pastas,  agendamento,  id_user);

        return EquipamentoDAO.update(equipamento);
    }

    public Object delete(Request request, Response response)
    {
        String id;

            try{
                id = request.queryParams("id");


            }catch  (Exception e){
                return "erro";
            }

            return EquipamentoDAO.delete_equipment(id);
    }

    public Object listar(Request request, Response response)
    {
            
        String id_user;

        try{
            id_user = request.queryParams("id_user");


        }catch  (Exception e){
            return "erro";
        }

        return EquipamentoDAO.listar_equipment(id_user);
    }


}