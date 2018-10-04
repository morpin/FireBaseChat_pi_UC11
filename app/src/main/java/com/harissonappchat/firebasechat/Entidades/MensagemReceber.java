package com.harissonappchat.firebasechat.Entidades;

import com.harissonappchat.firebasechat.Entidades.Mensagem;

public class MensagemReceber extends Mensagem {

    private Long hora;

    public MensagemReceber() {
    }

    public MensagemReceber(Long hora) {
        this.hora = hora;
    }

    public MensagemReceber(String mensagem, String urlFoto, String nome, String fotoPerfil, String type_mensagem, Long hora) {
        super(mensagem, urlFoto, nome, fotoPerfil, type_mensagem);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
