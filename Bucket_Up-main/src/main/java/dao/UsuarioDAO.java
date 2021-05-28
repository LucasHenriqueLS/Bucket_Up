package main.java.model;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UsuarioDAO{

    private static Sql2o db_conn;

    public void Conectar(){
        String dbHost = "127.0.0.1";
        String dbPort = "5432";
        String database = "projeto_backup";
        String dbUsername = "postgres";
        String dbPassword = "senha123";

        //Exemplo com Postgress
        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + database,
                dbUsername, dbPassword, new PostgresQuirks() {
            {
                converters.put(UUID.class, new UUIDConverter());
            }
        });
        db_conn = sql2o;
    }

    public boolean close() {
		boolean status = false;
		
		try {
			db_conn.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}


    public static String create_usuario(Usuario usuario){
        JSONObject out_json = new JSONObject();

        String query = "INSERT INTO usuario (nome,senha) VALUES ('"+usuario.nome+"','"+usuario.senha+"')";
        
        try(Connection conn = conn_db.beginTransaction()){
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        
        out_json.put("status",true);
        return out_json.toString();
    }


    public static String update_usuario(Usuario usuario){
        JSONObject out_json = new JSONObject();

        String query = "UPDATE usuario SET senha='"+usuariosenha+"' WHERE nome='"+usuario.nome+"'";
        
        try(Connection conn = conn_db.beginTransaction()){
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        
        out_json.put("status",true);
        return out_json.toString();
    }

    public static String  check_usuario_exist(String nome){
        JSONObject out_json = new JSONObject();

        String query = "SELECT id FROM usuario WHERE nome = '"+nome+"'";
    
        try(Connection conn = conn_db.beginTransaction()){
            List<Usuario> out = conn.createQuery(query).executeAndFetch(Usuario.class);
        
            if(out.size()>0){
                out_json.put("status",true);
                return out_json.toString();
            }else {
                out_json.put("status",false);
                return out_json.toString();
            }

        }
    }


    public static String  check_login(Usuario usuario){
        JSONObject out_json = new JSONObject();
    
        String query = "SELECT id FROM usuario WHERE nome = '"+usuario.nome+"' AND senha = '"+usuario.senha+"'";
    
        try(Connection conn = conn_db.beginTransaction()) {
            List<Usuario> out = conn.createQuery(query)
                    .addColumnMapping("id", "id_user")
                    .executeAndFetch(Usuario.class);
            
            if (out.size() > 0) {
                System.out.println(out.get(0).id_user);
                out_json.put("id", out.get(0).id_user);
                out_json.put("status", true);
                return out_json.toString();
            } else {
                //Caso o usuario nao exista, passa o valor "-1"
                out_json.put("id", "-1");
                out_json.put("status", false);
                return out_json.toString();
            }
        }
    }

}
