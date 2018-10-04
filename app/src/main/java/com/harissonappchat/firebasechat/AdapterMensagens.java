package com.harissonappchat.firebasechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.harissonappchat.firebasechat.Entidades.MensagemReceber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMensagens extends RecyclerView.Adapter<HolderMensagem> {

    private List<MensagemReceber> listMensagem = new ArrayList<>();
    private Context c;

    public AdapterMensagens(Context c) {
        this.c = c;
    }

    public void addMensagem(MensagemReceber m){
        listMensagem.add(m);
        notifyItemInserted(listMensagem.size());
    }

    @NonNull
    @Override
    public HolderMensagem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensagens,parent,false);
        return new HolderMensagem(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensagem holder, int position) {
        holder.getNome().setText(listMensagem.get(position).getNome());
        holder.getMensagem().setText(listMensagem.get(position).getMensagem());

        if(listMensagem.get(position).getType_mensagem().equals("2")){
            holder.getFotoMensagem().setVisibility(View.VISIBLE);
            holder.getMensagem().setVisibility(View.VISIBLE);
            Glide.with(c).load(listMensagem.get(position).getUrlFoto()).into(holder.getFotoMensagem());
        }else if(listMensagem.get(position).getType_mensagem().equals("1")){
            holder.getFotoMensagem().setVisibility(View.GONE);
            holder.getMensagem().setVisibility(View.VISIBLE);
        }
        if (listMensagem.get(position).getFotoPerfil().isEmpty()){
            holder.getFotoMensagemPerfil().setImageResource(R.mipmap.ic_launcher);

        }else{
            Glide.with(c).load(listMensagem.get(position).getFotoPerfil()).into(holder.getFotoMensagemPerfil());
        }

        Long codigoHora = listMensagem.get(position).getHora();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//ou pm ou am
        holder.getHora().setText(sdf.format(d));

    }

    @Override
    public int getItemCount() {
        return listMensagem.size();
    }
}
