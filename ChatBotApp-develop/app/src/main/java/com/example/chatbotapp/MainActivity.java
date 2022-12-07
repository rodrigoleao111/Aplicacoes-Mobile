package com.example.chatbotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.chatbotapp.datastructure.ChatBotIA;
import com.example.chatbotapp.datastructure.Perfil;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private ChatBotIA bot;
    private AlertDialog.Builder PopUpAjuda;
    private AlertDialog dialog;
    private ImageButton PopUpClose;
    private String txtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            txtname = extras.getString("txtname");
        }
        bot = leitortxt();
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        createNewPopUpAjuda();

        if(savedInstanceState == null) {
       /*     Fragment frag = new ChatBot();
            Bundle bundle = new Bundle();
            bundle.putSerializable("path",bot);
            frag.setArguments(bundle);
            FragmentManager fragManager = getSupportFragmentManager();
            fragManager.beginTransaction().replace(R.id.fragment_container,
                    frag).commit();

        */
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatBot(bot)).commit();


            navigationView.setCheckedItem(R.id.nav_chatbot);

        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_chatbot:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatBot(bot)).commit();
                break;

            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Perfil()).commit();
                break;

            case R.id.nav_config:
                Toast.makeText(MainActivity.this, "Fazer tela da configuração", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_atualizar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Documento()).commit();
                break;

            case R.id.nav_ajuda:
                createNewPopUpAjuda();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createNewPopUpAjuda(){
        PopUpAjuda = new AlertDialog.Builder(this);
        final View PopUpView = getLayoutInflater().inflate(R.layout.popup, null);

        PopUpAjuda.setView(PopUpView);
        dialog = PopUpAjuda.create();
        dialog.show();

        PopUpClose = (ImageButton) PopUpView.findViewById(R.id.botao_fechar);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ChatBotIA leitortxt(){
        try {
            ChatBotIA bot = new ChatBotIA(getExternalFilesDir(null),txtname);
            return bot;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Pergunta se o arquivo existe
    boolean hasExternalStoragePrivateFile(String s) {
        //pergunta existencia de arquivo com dado nome s
        File file = new File(
                getExternalFilesDir(null), s
        );
        return file.exists();
    }

}