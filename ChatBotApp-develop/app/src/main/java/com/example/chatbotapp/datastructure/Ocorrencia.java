package com.example.chatbotapp.datastructure;

import java.util.ArrayList;

public class Ocorrencia {


    private String chave;
    private ArrayList<String> ocorrencias;


    public Ocorrencia(String chave) {
        this.chave = chave;
        this.ocorrencias = new ArrayList<String>();
    }

    public String getChave() {
        return chave;
    }

    public ArrayList<String> getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(ArrayList<String> ocorrencias) {
        this.ocorrencias = ocorrencias;
    }
}
