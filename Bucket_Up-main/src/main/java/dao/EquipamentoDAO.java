package main.java.dao;

import main.java.model.Equipamento;
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
import java.util.UUID;;

class EquipamentoDAO{
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

    public static String create_equipment(Equipamento equipamento)
    {
        JSONObject out_json = new JSONObject();

        String query = "INSERT INTO equipamento (hostname,usuario_login,senha_login,ip,port,id_user,pastas,agendamento) VALUES ('"
        +equipamento.hostname+"','"
        +equipamento.usuario_login+"','"
        +equipamento.senha_login+"','"
        +equipamento.ip+"','"
        +equipamento.port+"',"
        +Integer.parseInt(equipamento.id_user)+"," +
        "'"+equipamento.pastas+"'," +
        "'"+equipamento.agendamento+"')";

        System.out.println(query);
        
        try(Connection conn = conn_db.beginTransaction())
        {
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        out_json.put("status",true);
        return out_json.toString();
    }

    public static String update_equipment(Equipamento equipamento)
    {
        JSONObject out_json = new JSONObject();

        String query = "UPDATE  equipamento SET hostname='"+equipamento.hostname+"' , usuario_login='"+equipamento.usuario_login+"' , " +
                "senha_login='"+equipamento.senha_login+"' , ip='"+equipamento.ip+"' , " +
                "port='"+equipamento.port+"' , pastas='"+equipamento.pastas+"'," +
                "agendamento='"+equipamento.agendamento+"'  WHERE id = "+equipamento.id+" ";

        System.out.println(query);
        
        try(Connection conn = conn_db.beginTransaction())
        {
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        
        out_json.put("status",true);
        return out_json.toString();
    }

    public static String delete_equipment(String id)
    {
        JSONObject out_json = new JSONObject();

        String query = "DELETE FROM equipamento WHERE id = "+id+" ";
        System.out.println(query);
        try(Connection conn = conn_db.beginTransaction()){
            conn.createQuery(query).executeUpdate();
            conn.commit();
        }
        out_json.put("status",true);
        return out_json.toString();
    }

    public static String listar_equipment(String id)
    {
        JSONObject out_json = new JSONObject();
        String query = "";

        if (id_user.equals("all")){
            query = "SELECT *  FROM equipamento ";

        }else {
            query = "SELECT *  FROM equipamento WHERE id_user = "+equipamento.id_user+" ";
        }

        System.out.println(query);
        List<Equipamento> out = null;

        JSONArray full_data = new JSONArray();
        
        try(Connection conn = conn_db.beginTransaction()){
            out = conn.createQuery(query)
                    .addColumnMapping("id", "id")
                    .addColumnMapping("hostname", "hostname")
                    .addColumnMapping("usuario_login", "usuario_login")
                    .addColumnMapping("senha_login", "senha_login")
                    .addColumnMapping("ip", "ip")
                    .addColumnMapping("port", "port")
                    .addColumnMapping("agendamento", "agendamento")
                    .executeAndFetch(Equipamento.class);
        }catch (Exception e)
        {
            System.out.println(e);
        }
        
        for (Equipamento s: out) {
            JSONObject tmp_json = new JSONObject();
            tmp_json.put("id",s.id);
            tmp_json.put("hostname",s.hostname);
            tmp_json.put("usuario_login",s.usuario_login);
            tmp_json.put("senha_login",s.senha_login);
            tmp_json.put("ip",s.ip);
            tmp_json.put("port",s.port);
            tmp_json.put("pastas",s.pastas);
            tmp_json.put("agendamento",s.agendamento);
            full_data.put(tmp_json);
        }
        
        //System.out.println(full_data.toString());
        return full_data.toString();
    }


}