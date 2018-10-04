package com.harissonappchat.firebasechat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMensagem extends RecyclerView.ViewHolder {

    private TextView nome;
    private TextView mensagem;
    private TextView hora;
    private CircleImageView fotoMensagemPerfil;
    private ImageView fotoMensagem;



    public HolderMensagem(@NonNull View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.nomeMensagem);
        mensagem = (TextView) itemView.findViewById(R.id.mensagemMensagem);
        hora = (TextView) itemView.findViewById(R.id.horaMensagem);
        fotoMensagemPerfil = (CircleImageView) itemView.findViewById(R.id.fotoPerfilMensagem);
        fotoMensagem = (ImageView) itemView.findViewById(R.id.mensagemFoto);
    }

    public TextView getNome() {
        return nome;
    }

    public void setNome(TextView nome) {
        this.nome = nome;
    }

    public TextView getMensagem() {
        return mensagem;
    }

    public void setMensagem(TextView mensagem) {
        this.mensagem = mensagem;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public CircleImageView getFotoMensagemPerfil() {
        return fotoMensagemPerfil;
    }

    public ImageView getFotoMensagem() {
        return fotoMensagem;
    }

    public void setFotoMensagem(ImageView fotoMensagem) {
        this.fotoMensagem = fotoMensagem;
    }

    public void setFotoMensagemPerfil(CircleImageView fotoMensagemPerfil) {
        this.fotoMensagemPerfil = fotoMensagemPerfil;


    }
}
