package com.example.padariaseujoca;

public class Comanda {
    public String nome;
    public String endereco;
    private int qtdPaoFrances;
    private int qtdQuejoCoalho;
    private int qtdBoloDeMilho;

    public Comanda(String nome, String endereco, int qtdPaoFrances, int qtdQuejoCoalho, int qtdBoloDeMilho) {
        this.nome = nome;
        this.endereco = endereco;
        this.qtdPaoFrances = qtdPaoFrances;
        this.qtdQuejoCoalho = qtdQuejoCoalho;
        this.qtdBoloDeMilho = qtdBoloDeMilho;
    }

    public Comanda(){}

    /**
     * Métodos para adicionar item na comanda
     */
    public void addPaoFrances(){
        this.setQtdPaoFrances(this.qtdPaoFrances + 1);
    }

    public void addQueijoCoalho(){
        this.setQtdQuejoCoalho(this.qtdQuejoCoalho + 1);
    }

    public void addBoloDeMilho(){
        this.setQtdBoloDeMilho(this.qtdBoloDeMilho + 1);
    }

    public String impressaoComanda(){
        String comanda = "nada ainda";
        return comanda;
    }

    /**
     * Getters e Setters
     */
    public int getQtdPaoFrances() {
        return qtdPaoFrances;
    }

    public void setQtdPaoFrances(int qtdPaoFrances) {
        this.qtdPaoFrances = qtdPaoFrances;
    }

    public int getQtdQuejoCoalho() {
        return qtdQuejoCoalho;
    }

    public void setQtdQuejoCoalho(int qtdQuejoCoalho) {
        this.qtdQuejoCoalho = qtdQuejoCoalho;
    }

    public int getQtdBoloDeMilho() {
        return qtdBoloDeMilho;
    }

    public void setQtdBoloDeMilho(int qtdBoloDeMilho) {
        this.qtdBoloDeMilho = qtdBoloDeMilho;
    }
}
