package com.example.puzzle_number;

import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro {

    private Bloco grid[][];
    private int ndeMovimentos=0;
    private int poszeroX;
    private int poszeroY;
    private int Nk;

    public Tabuleiro(int k){
        Nk=k;
        criaTabuleiro();
    }

    private void criaTabuleiro(){
        //cria tab
        int k=Nk;
        int numero=1;
        grid= new Bloco[k][k];
        for(int i=0;i<k;i++){
            for(int j=0;j<k;j++){
                if(i==Nk-1 && j==Nk-1){
                    grid[i][j]=new Bloco(0,true);
                    poszeroX=i;
                    poszeroY=j;
                } else {
                    grid[i][j]= new Bloco(numero,false);
                }
                numero++;
            }
        }
        // organizar tab
        numero=40*((Nk-1)*(Nk-1));
        k=0;
        while(k!=numero){
            int gerador = new Random().nextInt(4);
            // 0 cima
            // 1 baixo
            // 2 esquerda
            // 3 direita
            if(gerador==0 && poszeroX==Nk-1){

            } else if(gerador==1 && poszeroX==0){

            } else if(gerador==2 && poszeroY==Nk-1){

            } else if(gerador==3 && poszeroY==0){

            } else {
                switch(gerador){
                    case 0:
                        movimentar(poszeroX+1,poszeroY,"cima");
                        poszeroX=poszeroX+1;
                        break;
                    case 1:
                        movimentar(poszeroX-1,poszeroY,"baixo");
                        poszeroX=poszeroX-1;
                        break;
                    case 2:
                        movimentar(poszeroX,poszeroY+1,"esquerda");
                        poszeroY=poszeroY+1;
                        break;
                    case 3:
                        movimentar(poszeroX,poszeroY-1,"direita");
                        poszeroY=poszeroY-1;
                        break;
                }
                k++;
            }

        }
        while(poszeroX!=Nk-1){
            movimentar(poszeroX+1,poszeroY,"cima");
            poszeroX=poszeroX+1;
            while(poszeroY!=Nk-1){
                movimentar(poszeroX,poszeroY+1,"esquerda");
                poszeroY=poszeroY+1;
            }
        }
        ndeMovimentos=0;
    }
    public void movimentar(int x, int y, String mov){
        if(mov.equals("cima")){
            if(ismovimentovalido(x,y,mov)){
                grid[x-1][y].setValor(grid[x][y].getValor());
                grid[x][y].setValor(0);
                ndeMovimentos++;
            }
        } else if(mov.equals("baixo")){
            if(ismovimentovalido(x,y,mov)){
                grid[x+1][y].setValor(grid[x][y].getValor());
                grid[x][y].setValor(0);
                ndeMovimentos++;
            }
        } else if(mov.equals("esquerda")){
            if(ismovimentovalido(x,y,mov)){
                grid[x][y-1].setValor(grid[x][y].getValor());
                grid[x][y].setValor(0);
                ndeMovimentos++;
            }
        } else if(mov.equals("direita")){
            if(ismovimentovalido(x,y,mov)){
                grid[x][y+1].setValor(grid[x][y].getValor());
                grid[x][y].setValor(0);
                ndeMovimentos++;
            }
        }
    }

    public Boolean ismovimentovalido(int x, int y, String mov){
        if(mov.equals("cima")){
            if(x==0){
                return false;
            } else if(grid[x-1][y].getValor()==0){
                return true;
            } else {
                return false;
            }
        } else if(mov.equals("baixo")){
            if(x==Nk-1){
                return false;
            } else if(grid[x+1][y].getValor()==0){
                return true;
            } else {
                return false;
            }
        } else if (mov.equals("esquerda")){
            if(y==0){
                return false;
            } else if(grid[x][y-1].getValor()==0){
                return true;
            } else {
                return false;
            }
        } else if(mov.equals("direita")){
            if(y==Nk-1){
                return false;
            } else if(grid[x][y+1].getValor()==0){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Boolean isTabuleiroOrdenado(){
        int numero = 1;
        for(int x=0;x<Nk;x++){
            for(int y=0;y<Nk;y++){
                if(numero==grid[x][y].getValor()){

                } else if(numero==(Nk*Nk)) {
                    if(0==grid[x][y].getValor()){
                        return true;
                    }
                } else {
                    return false;
                }
                numero++;
            }
        }

        return true;
    }


    public Bloco[][] getGrid() {
        return grid;
    }

    public void setGrid(Bloco[][] grid) {
        this.grid = grid;
    }

    public int getNdeMovimentos() {
        return ndeMovimentos;
    }

    public void setNdeMovimentos(int ndeMovimentos) {
        this.ndeMovimentos = ndeMovimentos;
    }
}
