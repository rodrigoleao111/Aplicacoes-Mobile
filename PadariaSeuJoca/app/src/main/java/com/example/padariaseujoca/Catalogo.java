package com.example.padariaseujoca;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Catalogo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        //Receber informações de cadastro da MainActivity
        Intent intentReceberCadastro = getIntent();
        Bundle dadosDoPedido = intentReceberCadastro.getExtras();
        String nome = dadosDoPedido.getString("kNome");
        String endereco = dadosDoPedido.getString("kEndereco");

        /**
         * Transitar entre categorias de produtos.
         * Descrição: Ao clicar em botão, fazer um scroll para a View específica do topo de categoria
         */
        Button goToPaos = (Button) findViewById(R.id.btnGoToPao);
        Button goToQueijos = (Button) findViewById(R.id.btnGoToQueijos);
        Button goToBolos = (Button) findViewById(R.id.btnGoToBolos);
        ScrollView catalogo = (ScrollView) findViewById(R.id.scroll_view);
        goToPaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToView(catalogo,findViewById(R.id.textView3));
            }
        });
        goToQueijos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToView(catalogo,findViewById(R.id.textView23));
            }
        });
        goToBolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToView(catalogo,findViewById(R.id.textView30));
            }
        });

        /**
         * Adicionar itens na comanda
         */
        Comanda comanda = new Comanda(nome, endereco,0, 0, 0);
        // Adicionar Pão Frances
        Button addPaoFrances = (Button) findViewById(R.id.adicionaPaoFrances);
        addPaoFrances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comanda.addPaoFrances();
                Toast.makeText(Catalogo.this, "Pão Fances adicionado ao carrinho.", Toast.LENGTH_SHORT).show();
            }
        });
        // Adicionar Quejo Coalho
        Button addQuejoCoalho = (Button) findViewById(R.id.adicionaQueijCoalho);
        addQuejoCoalho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comanda.addQueijoCoalho();
                Toast.makeText(Catalogo.this, "Queijo Coalho adicionado ao carrinho.", Toast.LENGTH_SHORT).show();
            }
        });
        // Adicionar Bolo de milho
        Button addBoloDeMilho = (Button) findViewById(R.id.adicionaBoloDeMilho);
        addBoloDeMilho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comanda.addBoloDeMilho();
                Toast.makeText(Catalogo.this, "Bolo de Milho adicionado ao carrinho.", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Ir para Carrinho
         * Ao clicar em irParaCarrinho, o método irá adicionar as quantidades de cada item no Bundle dadosDoPedido
         */
        FloatingActionButton irParaCarrinho = (FloatingActionButton) findViewById(R.id.floatingBtnGotToCarrinho);
        irParaCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dadosDoPedido.putInt("kPaoFrances", comanda.getQtdPaoFrances());
                dadosDoPedido.putInt("kQueijoCoalho", comanda.getQtdQuejoCoalho());
                dadosDoPedido.putInt("kBoloDeMilho", comanda.getQtdBoloDeMilho());

                Intent intentCarrinho = new Intent(getApplicationContext(), Carrinho.class);
                intentCarrinho.putExtras(dadosDoPedido);
                startActivity(intentCarrinho);
            }
        });

    }

    // MÉTODOS ----------------------------------------------------------------------------

    /**
     * Método para Scrolar a uma View específica.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view View a qual queremos ir.
     */
    private void scrollToView(final ScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumulated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    /**
     * Imprimir Toast "Produto indisponível"
     * @param view
     */
    public void produtoIndisponivel(View view) {
        Toast.makeText(Catalogo.this, "Produto indisponível.", Toast.LENGTH_LONG).show();
    }

    /**
     * Ir para Activity PaoFrances
     * @param view
     */
    public void goToPaoFrances(View view) {
        Intent detalhesPaoFrances = new Intent(getApplicationContext(), PaoFrances.class);
        startActivity(detalhesPaoFrances);
    }

}