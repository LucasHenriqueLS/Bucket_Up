package main.java.model;

import java.util.*;

class Usuario{
    public Integer id_user;
    public String  nome;
    public String senha;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
    
    public Integer getId_user() {
        return id_user;
    }
    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
