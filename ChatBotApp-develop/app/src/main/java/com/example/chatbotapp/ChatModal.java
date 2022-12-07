package com.example.chatbotapp;

import android.graphics.Bitmap;

public class ChatModal {

    private String mensagem;
    private String enviar;
    private Bitmap imagembitmap;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getEnviar() {
        return enviar;
    }

    public void setEnviar(String enviar) {
        this.enviar = enviar;
    }

    public ChatModal(String mensagem, String enviar) {
        this.mensagem = mensagem;
        this.enviar = enviar;
    }

    public ChatModal(Bitmap bitmap, String enviar){
        this.imagembitmap = bitmap;
        this.enviar = enviar;
    }

    public Bitmap getImagembitmap() {
        return imagembitmap;
    }

    public void setImagembitmap(Bitmap imagembitmap) {
        this.imagembitmap = imagembitmap;
    }
}
