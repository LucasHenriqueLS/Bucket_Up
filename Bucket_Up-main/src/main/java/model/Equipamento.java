package main.java.model;

import java.util.*;

class Equipamento{

    public Integer id;
    public String  hostname;
    public String  usuario_login;
    public String  senha_login;
    public String  ip;
    public String  port;
    public String  pastas;
    public String  agendamento;
    public String  id_user;    

    public Equipamento(String hostname, String usuario_login, String senha_login, String ip, String port,
            String pastas, String agendamento, String id_user) {
        this.hostname = hostname;
        this.usuario_login = usuario_login;
        this.senha_login = senha_login;
        this.ip = ip;
        this.port = port;
        this.pastas = pastas;
        this.agendamento = agendamento;
        this.id_user = id_user;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getHostname() {
        return hostname;
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }


    public String getUsuario_login() {
        return usuario_login;
    }


    public void setUsuario_login(String usuario_login) {
        this.usuario_login = usuario_login;
    }


    public String getSenha_login() {
        return senha_login;
    }


    public void setSenha_login(String senha_login) {
        this.senha_login = senha_login;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public String getPort() {
        return port;
    }


    public void setPort(String port) {
        this.port = port;
    }


    public String getPastas() {
        return pastas;
    }


    public void setPastas(String pastas) {
        this.pastas = pastas;
    }


    public String getAgendamento() {
        return agendamento;
    }


    public void setAgendamento(String agendamento) {
        this.agendamento = agendamento;
    }


    public String getId_user() {
        return id_user;
    }


    public void setId_user(String id_user) {
        this.id_user = id_user;
    }   
}