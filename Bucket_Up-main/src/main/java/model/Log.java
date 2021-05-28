package main.java.model;

import java.util.*;

class Log{
    
    Integer ID;
    Integer ID_EQUIPAMENT;
    String hora_execucao;
    String nome_arquivo;
    
    public Log(Integer iD_EQUIPAMENT, String hora_execucao, String nome_arquivo) {
        ID_EQUIPAMENT = iD_EQUIPAMENT;
        this.hora_execucao = hora_execucao;
        this.nome_arquivo = nome_arquivo;
    }

    public Integer getID() {
        return ID;
    }
    public void setID(Integer iD) {
        ID = iD;
    }
    public Integer getID_EQUIPAMENT() {
        return ID_EQUIPAMENT;
    }
    public void setID_EQUIPAMENT(Integer iD_EQUIPAMENT) {
        ID_EQUIPAMENT = iD_EQUIPAMENT;
    }
    public String getHora_execucao() {
        return hora_execucao;
    }
    public void setHora_execucao(String hora_execucao) {
        this.hora_execucao = hora_execucao;
    }
    public String getNome_arquivo() {
        return nome_arquivo;
    }
    public void setNome_arquivo(String nome_arquivo) {
        this.nome_arquivo = nome_arquivo;
    }
}
