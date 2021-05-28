package main.java.service;

import dao.UsuarioDAO;
import model.Usuario;
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

public class UsuarioService {

	private UsuarioDAO UsuarioDAO;

	public UsuarioService() {
		
			UsuarioDAO = new UsuarioDAO();
			UsuarioDAO.Conectar();
	}

    public Object add(Request request, Response response)
    {
            String nome;
            String senha;

            try{
                nome = request.queryParams("usuario");
                senha = request.queryParams("senha");

            }catch  (Exception e){
                return "erro";
            }

            Usuario usuario = new Usuario(nome,senha);

            return UsuarioDAO.create_usuario(usuario);
    }

    public Object update(Request request, Response response)
    {
            String nome;
            String senha;

            try{
                nome = request.queryParams("usuario");
                senha = request.queryParams("senha");

            }catch  (Exception e){
                return "erro";
            }

            Usuario usuario = new Usuario(nome,senha);

            return UsuarioDAO.update_usuario(usuario);
    }

    public Object checkU(Request request, Response response)
    {
        String nome;

            try{
                nome = request.queryParams("usuario");

            }catch  (Exception e){
                return "erro";
            }

            return UsuarioDAO.check_usuario_exist(nome);
    }

    public Object checkL(Request request, Response response)
    {
            String nome;
            String senha;

            try{
                nome = request.queryParams("usuario");
                senha = request.queryParams("senha");

            }catch  (Exception e){
                return "erro";
            }

            Usuario usuario = new Usuario(nome,senha);

            return UsuarioDAO.check_login(usuario);
    }


}