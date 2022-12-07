package com.example.chatbotapp.datastructure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatBotIA implements Serializable {

    ArrayList<Mapa> mapa = new ArrayList<Mapa>();
    String pathfile;
    private String opçãouser = "";

    public ChatBotIA(File bula, String s) throws Exception {
        pathfile = bula.getPath()+"/"+"P2Z_"+s;
        leitor(pathfile+"/"+s+".txt");
    }
    private void leitor(String path) throws Exception{

        Scanner scanner = new Scanner(new File(path));
        String line;
        int x =-1;
        String concatenador="";
        String chave ="";
        ArrayList<Bitmap> listadeimg = new ArrayList<Bitmap>();
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.length()>1) {
                if(isCase(line)){
                    if(chave!=""){
                        treeFunction(chave,concatenador,listadeimg);
                        listadeimg = new ArrayList<Bitmap>();
                    }
                    chave= line;
                    concatenador="";
                } else if(line.contains("!!!!!")){
                    listadeimg.add(imageParaBitmap(line.substring(5)));
                } else if (chave!=""){
                    concatenador+=line+"\n";
                }
            }
        }
        if(chave!="" && concatenador!=""){
            treeFunction(chave,concatenador,listadeimg);
        }

        }

    public String resposta(@NonNull String user){
        if(user.length()==0 && opçãouser==""){
            return mostrarOpções();
        } else if(isSearch(user)){
           ArrayList<Ocorrencia> lista = deepSearch(user.substring(7));
           String concatenador ="\n";
           if(lista.size()==0){
               return "nada encontrado com a palavra:\n"+user.substring(7);
           }
           for(int x=0;x<lista.size();x++){
               concatenador+=lista.get(x).getChave()+"\n\n";
               for(int y=0;y<lista.get(x).getOcorrencias().size();y++){
                   concatenador+=lista.get(x).getOcorrencias().get(y)+"\n\n";
               }
           }
            return concatenador;
        }
        else if(isValidMessage(user)) {
            if(user.equals("0") && opçãouser.equals("")){
                return mostrarOpções();
            }
            Integer opção = Integer.parseInt(user);
            String opçãouser2 = opçãouser;
            if (opção == -1) {
                opçãouser = "";
                return "resetado";
            } else if(opção == 0 && opçãouser2.length()>0){
                String concatenador ="";
                String [] op = opçãouser2.split("\\.");
                for(int x=0;x<op.length-1;x++){
                    concatenador+=op[x]+".";
                }
                opçãouser2=concatenador;
            } else {
                if (opçãouser2 == "") {
                    opçãouser2 = opção.toString() + ".";
                } else {
                    opçãouser2 += opção.toString() + ".";
                }
            }
            if(opçãouser2==""){
                opçãouser=opçãouser2;
               return mostrarOpções();
            }
            ArrayList<Integer> list = userOptionstr(opçãouser2);
            Mapa ponteiro = null;
            for (int x = 0; x < list.size(); x++) {
                if (x == 0 && list.get(x)<mapa.size()) {
                        ponteiro = mapa.get(list.get(x));

                } else if(x != 0 && list.get(x)<ponteiro.getSubseções().size()){
                    ponteiro = ponteiro.getSubseções().get(list.get(x));
                } else {
                    return isInvalido();
                }
            }
            String last = "";
            if (ponteiro != null) {
                last = ponteiro.getInfo() + "\n\n";
                if (ponteiro.getSubseções().size() > 0) {
                    for (int x = 0; x < ponteiro.getSubseções().size(); x++) {
                        last += ponteiro.getSubseções().get(x).getChave() + "\n";
                    }
                }
            }
            opçãouser=opçãouser2;
            return last;
        } else {
            return isInvalido();
        }
    }

    private ArrayList<Integer> userOptionstr(String opçãouser2){
        ArrayList<Integer> list = new ArrayList<Integer>();
        String concatenador = "";
        for (int x = 0; x < opçãouser2.length(); x++) {
            if (Character.isDigit(opçãouser2.charAt(x))) {
                concatenador += Character.toString(opçãouser2.charAt(x));
            } else if (opçãouser2.charAt(x) == '.') {
                list.add(Integer.parseInt(concatenador) - 1);
                concatenador = "";
            }
        }
        return list;
    }

    private boolean isValidMessage(String user){
        if(user.equals("-1")) {
            return true;
        }
            for (int x = 0; x < user.length(); x++) {
                if (x == 0 && user.charAt(x) == '-') {
                    continue;
                } else if (user.charAt(x) == '.' && x == 0) {
                    continue;
                } else if (Character.isDigit(user.charAt(x))) {
                    continue;
                } else {
                    return false;
                }
            }
        return true;
    }

    private String isInvalido(){
        return "Invalido";
    }


    private Boolean isCase(String s){
        int tamanho = s.length();
        if(s.length()<2){
            return false;
        } else if(Character.isDigit(s.charAt(0)) && s.charAt(1)=='.'){
            return true;
        } else {
            return false;
        }
    }
    private String onlyCase(String s){
        String concatenador="";
        for(int x=0;x<s.length();x++){
            if(Character.isDigit(s.charAt(x)) || s.charAt(x)=='.'){
                concatenador+=Character.toString(s.charAt(x));
            } else {
                break;
            }
        }
        return concatenador;
    }
    private int howCase(String s){
        int caso = 0;
        for(int x=0;x<s.length();x++){
            if(s.charAt(x)=='.'){
                caso++;
            }
        }
        return caso-1;
    }

    private String mostrarOpções(){
        String concatenador="-1 reseta\n\n" +
                "0 volta\n\n";
        for(int x=0;x<mapa.size();x++){
            concatenador+=mapa.get(x).getChave()+"\n";
        }
        return concatenador;
    }


    private void treeFunction(String chave, String concatenador, ArrayList<Bitmap> listaimg){
        int profundidade = howCase(onlyCase(chave));
        if(profundidade==0){
            ArrayList<Mapa> ponteiro = mapa;
            ponteiro.add(new Mapa(chave));
            int tamanho =ponteiro.size()-1;
            ponteiro.get(tamanho).setInfo(concatenador);
            ponteiro.get(tamanho).setImagens(listaimg);

        } else {
            Mapa ponteiro = mapa.get(mapa.size()-1);
            for(int x=1;x<profundidade;x++){
                ponteiro = ponteiro.getSubseções().get(ponteiro.getSubseções().size()-1);
            }
            ponteiro.getSubseções().add(new Mapa(chave));
            int tamanho2 = ponteiro.getSubseções().size()-1;
            ponteiro.getSubseções().get(tamanho2).setInfo(concatenador);
            ponteiro.getSubseções().get(tamanho2).setImagens(listaimg);
        }

    }

    
    // ALGORITMOS DE BUSCA \/
    private boolean isSearch(String s){
        String search = "search:";
        String tipo = s.toLowerCase();
        for (int x=0;x<tipo.length();x++){
            if(x>=search.length()){
                return true;
            } else if(search.charAt(x)==tipo.charAt(x)){
                continue;
            } else {
                return false;
            }
        }

        return true;
    }

    private ArrayList<Ocorrencia> deepSearch(String s){
        ArrayList<Mapa> ponteiro1 = mapa;
        ArrayList<Ocorrencia> resultado = new ArrayList<Ocorrencia>();
            for (int x = 0; x < ponteiro1.size(); x++) {
                if(ponteiro1.get(x).getInfo()!="" && ponteiro1.get(x).getInfo().contains(s)){
                    resultado.add(new Ocorrencia(ponteiro1.get(x).getChave()));
                    ArrayList<String> listagemOcorStr = listarOcorStr(ponteiro1.get(x).getInfo(),s);
                    resultado.get(resultado.size()-1).setOcorrencias(listagemOcorStr);
                }
                ArrayList<Ocorrencia> vindo = searchAux(s,ponteiro1.get(x));
                if (vindo.size()!=0){
                    resultado.addAll(vindo);
                }
            }
            return resultado;
    }

    private ArrayList<Ocorrencia> searchAux(String s,Mapa pontaux){
        ArrayList<Mapa> ponteiroaux = pontaux.getSubseções();
        ArrayList<Ocorrencia> resultado = new ArrayList<Ocorrencia>();
        for( int x=0;x< ponteiroaux.size();x++){
            if(ponteiroaux.get(x).getInfo()!="" && ponteiroaux.get(x).getInfo().contains(s)){
                resultado.add(new Ocorrencia(ponteiroaux.get(x).getChave()));
                ArrayList<String> listagemOcorStr = listarOcorStr(ponteiroaux.get(x).getInfo(),s);
                resultado.get(resultado.size()-1).setOcorrencias(listagemOcorStr);
            }
            ArrayList<Ocorrencia> vindo = searchAux(s,ponteiroaux.get(x));
            if (vindo.size()!=0){
                resultado.addAll(vindo);
            }
        }
        return resultado;
    }

    private ArrayList<String> listarOcorStr(String info,String s){
        ArrayList<String> listagemOcorStr = new ArrayList<String>();
        String[] infoemlinhas = info.split("\n");
        String S = s.toUpperCase();
        for(int x=0;x< infoemlinhas.length;x++){
            if(infoemlinhas[x].contains(s)){
                listagemOcorStr.add(infoemlinhas[x].replaceAll(s,S));
            }
        }
        return listagemOcorStr;
    }

    // ALGORITMOS DE BUSCA /\

    // CODIGO PARA A IMAGENS \/

    private Bitmap imageParaBitmap(String filename){
        String pathfileimg = pathfile+"/"+filename;
        Bitmap bitmap = BitmapFactory.decodeFile(pathfileimg);
        return bitmap;
    }

    public ArrayList<Bitmap> retornarimagens(){
        ArrayList<Integer> list = userOptionstr(opçãouser);
        if(list.size()==0){
            return new ArrayList<Bitmap> ();
        }
        Mapa ponteiro = null;

        for(int x=0;x<list.size();x++){
            if(x==0 && list.get(x)<mapa.size()){
                ponteiro = mapa.get(list.get(x));
            } else if( x!=0 && list.get(x)<ponteiro.getSubseções().size()){
                ponteiro = ponteiro.getSubseções().get(list.get(x));
            } else {
                break;
            }
        }
        return ponteiro.getImagens();
    }


}