package com.example.chatbotapp.datastructure;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class Mapa {

    private ArrayList<Mapa> Subseções = new ArrayList<Mapa>();
    private String chave;
    private String info ;
    private ArrayList<Bitmap> imagens = new ArrayList<>();

    Mapa (String chave){
        this.chave = chave;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public ArrayList<Mapa> getSubseções() {
        return Subseções;
    }

    public void setSubseções(ArrayList<Mapa> subseções) {
        this.Subseções = subseções;
    }

    public ArrayList<Bitmap> getImagens() {
        return imagens;
    }

    public void setImagens(ArrayList<Bitmap> imagens) {
        this.imagens = imagens;
    }
}
