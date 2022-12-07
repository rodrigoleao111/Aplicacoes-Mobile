package com.example.padariaseujoca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Carrinho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // PREÇOS
        double precoPaoFrances = 0.50;
        double precoQueijoCoalho = 27.50;
        double precoBoloDeMilho = 32.75;

        // RECEBIMENTO E ATRIBUIÇÃO DE DADOS
        Bundle dadosDoPedido = getIntent().getExtras();
        Comanda comanda = new Comanda();
        comanda.nome = dadosDoPedido.getString("kNome");
        comanda.endereco = dadosDoPedido.getString("kEndereco");
        comanda.setQtdPaoFrances(dadosDoPedido.getInt("kPaoFrances"));
        comanda.setQtdQuejoCoalho(dadosDoPedido.getInt("kQueijoCoalho"));
        comanda.setQtdBoloDeMilho(dadosDoPedido.getInt("kBoloDeMilho"));
        // FIM DO RECEBIMENTO E ATRIBUIÇÃO DE DADOS

        // ATRIBUTOS DE VALORES PARCIAIS
        double totalPaoFrances = valorParcial(precoPaoFrances, comanda.getQtdPaoFrances());
        double totalQueijoCoalho = valorParcial(precoQueijoCoalho, comanda.getQtdQuejoCoalho());
        double totalBoloDeMilho = valorParcial(precoBoloDeMilho, comanda.getQtdBoloDeMilho());

        // QUANTIDADES
        TextView qtdPaoFrances = (TextView) findViewById(R.id.qtdPaoFrances);
        qtdPaoFrances.setText(String.valueOf(comanda.getQtdPaoFrances()));

        TextView qtdQuejoCoalho = (TextView) findViewById(R.id.qtdQueijoCoalho);
        qtdQuejoCoalho.setText(String.valueOf(comanda.getQtdQuejoCoalho()));

        TextView qtdBoloDeMilho = (TextView) findViewById(R.id.qtdBoloDeMilho);
        qtdBoloDeMilho.setText(String.valueOf(comanda.getQtdBoloDeMilho()));

        // VALORES PARCIAIS
        TextView valorPaoFrances = (TextView) findViewById(R.id.valorPaoFrances);
        valorPaoFrances.setText(Double.toString(totalPaoFrances));

        TextView valorQueijoCoalho = (TextView) findViewById(R.id.valorQueijoCoalho);
        valorQueijoCoalho.setText(Double.toString(totalQueijoCoalho));

        TextView valorBoloDeMilho = (TextView) findViewById(R.id.valorBoloDeMilho);
        valorBoloDeMilho.setText(Double.toString(totalBoloDeMilho));

        // VALOR TOTAL
        double totalCompras = totalPaoFrances + totalQueijoCoalho + totalBoloDeMilho;
        TextView valorTotal = (TextView) findViewById(R.id.valorTotal);
        valorTotal.setText(Double.toString(totalCompras));

        // NOME
        TextView txtNome = (TextView) findViewById(R.id.nomeDoCliente);
        valorBoloDeMilho.setText(comanda.nome);

        // ENDEREÇO
        TextView txtEnd = (TextView) findViewById(R.id.enderecoDoCliente);
        txtEnd.setText(comanda.endereco);

    }

    private double valorParcial(double preco, int quantidade){
        double resultado = preco*quantidade;
        return resultado;
    }
}