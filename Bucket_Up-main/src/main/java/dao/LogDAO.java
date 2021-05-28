package main.java.model;

import main.java.model.Log;
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

class LogDAO{
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
                // make sure we use default UUID converter.
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


    public static String listar_log(String id_equip)
    {
        JSONObject out_json = new JSONObject();

        String query = "SELECT hora_execucao,nome_arquivo  FROM log_event WHERE id_equipment = "+id_equip+" ";
        System.out.println(query);
        List<Log> out = null;

        JSONArray full_data = new JSONArray();
        try(Connection conn = conn_db.beginTransaction()){
            out = conn.createQuery(query)
                    .addColumnMapping("hora_execucao", "hora_execucao")
                    .addColumnMapping("nome_arquivo", "nome_arquivo")
                    .executeAndFetch(Log.class);
        }catch (Exception e){
            System.out.println(e);

        }
        for (Log s: out) {
            JSONObject tmp_json = new JSONObject();
            tmp_json.put("hora_execucao",s.hora_execucao);
            tmp_json.put("nome_arquivo",s.nome_arquivo);
            full_data.put(tmp_json);
        }
        System.out.println(full_data.toString());
        return full_data.toString();
    }


    public static String create_log_event(Log log)
    {
        JSONObject out_json = new JSONObject();

        String query = "INSERT into log_event (hora_execucao,nome_arquivo,id_equipment) VALUES ('"
                +log.hora_execucao+"','"
                +log.nome_arquivo+"',"
                +log.ID_EQUIPAMENT+")";
        System.out.println(query);
        try(Connection conn = conn_db.beginTransaction()){
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        out_json.put("status",true);
        return out_json.toString();
    }
   
}
