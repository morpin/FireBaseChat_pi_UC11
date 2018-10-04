package com.harissonappchat.firebasechat.Entidades;

import com.harissonappchat.firebasechat.Entidades.Mensagem;

import java.util.Map;

public class MensagemEnviar extends Mensagem {
    private Map hora;

    public MensagemEnviar() {
    }

    public MensagemEnviar(Map hora) {
        this.hora = hora;
    }

    public MensagemEnviar(String mensagem, String nome, String fotoPerfil, String type_mensagem, Map hora) {
        super(mensagem, nome, fotoPerfil, type_mensagem);
        this.hora = hora;
    }

    public MensagemEnviar(String mensagem, String urlFoto, String nome, String fotoPerfil, String type_mensagem, Map hora) {
        super(mensagem, urlFoto, nome, fotoPerfil, type_mensagem);
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }
}
