package com.harissonappchat.firebasechat.Entidades;

public class Mensagem {

    private String mensagem;
    private String urlFoto;
    private String nome;
    private String fotoPerfil;
    private String type_mensagem;


    public Mensagem() {
    }

    public Mensagem(String mensagem, String nome, String fotoPerfil, String type_mensagem) {
        this.mensagem = mensagem;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.type_mensagem = type_mensagem;

    }

    public Mensagem(String mensagem, String urlFoto, String nome, String fotoPerfil, String type_mensagem) {
        this.mensagem = mensagem;
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.fotoPerfil = fotoPerfil;
        this.type_mensagem = type_mensagem;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensagem() {
        return type_mensagem;
    }

    public void setType_mensagem(String type_mensagem) {
        this.type_mensagem = type_mensagem;
    }


    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
