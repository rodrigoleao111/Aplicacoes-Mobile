package com.example.puzzle_number;

public class Bloco {
    private int valor;
    private Boolean verdade;

    Bloco(int valor2, Boolean verdade2){
        valor=valor2;
        verdade=verdade2;
    }
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Boolean getVerdade() {
        return verdade;
    }

    public void setVerdade(Boolean verdade) {
        this.verdade = verdade;
    }
}
