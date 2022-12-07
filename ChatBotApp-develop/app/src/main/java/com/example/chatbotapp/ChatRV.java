package com.example.chatbotapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.bouncycastle.asn1.x509.Holder;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;

public class ChatRV extends RecyclerView.Adapter {
    private ArrayList<ChatModal> chatModalArrayList;
    private Context context;

    public ChatRV(ArrayList<ChatModal> chatModalArrayList, Context context) {
        this.chatModalArrayList = chatModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_mensagem_rv, parent, false);
                return new MensagemUser(view);

            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_mensagem_rv, parent, false);
                return new MensagemBot(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_img_rv,parent,false);
                return new ImageBot(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModal chatModal = chatModalArrayList.get(position);
        switch(chatModal.getEnviar()){
            case "user":
                ((MensagemUser)holder).userTV.setText(chatModal.getMensagem());
                break;

            case "bot":
                ((MensagemBot)holder).botTV.setText(chatModal.getMensagem());
                break;
            case "imgbot":
                ((ImageBot)holder).imageview.setImageBitmap(chatModal.getImagembitmap());
        }
    }

    @Override
    public int getItemViewType(int position){
        switch (chatModalArrayList.get(position).getEnviar()){
            case "user":
                return 0;

            case "bot":
                return 1;

            case "imgbot":
                return 2;
            default:
                return -1;

        }
    }

    @Override
    public int getItemCount() {
        return chatModalArrayList.size();
    }

    public static class MensagemUser extends RecyclerView.ViewHolder{
        TextView userTV;

        public MensagemUser(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.mensagem_usuario);
        }
    }

    public static class MensagemBot extends RecyclerView.ViewHolder{
        TextView botTV;

        public MensagemBot(@NonNull View itemView) {
            super(itemView);
            botTV = itemView.findViewById(R.id.mensagem_bot);
        }
    }

    public static class ImageBot extends RecyclerView.ViewHolder{

        ImageView imageview;
        public ImageBot(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageView6);
        }
    }



}
