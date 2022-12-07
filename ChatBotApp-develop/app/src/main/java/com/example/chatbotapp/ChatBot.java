package com.example.chatbotapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.datastructure.ChatBotIA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChatBot extends Fragment {

    private RecyclerView chat;
    private EditText MensagemUsuario;
    private FloatingActionButton MensagemEnviar;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private final String BOT_IMG_KEY = "imgbot";
    private ArrayList<ChatModal>chatModalArrayList;
    private ChatRV chatRV;
    private ChatBotIA bot;

    public ChatBot(ChatBotIA bot) {
        this.bot = bot;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        chat = view.findViewById(R.id.RecV_ChatBot);
        MensagemUsuario = view.findViewById(R.id.mensagem);
        MensagemEnviar = view.findViewById(R.id.mensagem_enviar);
        chatModalArrayList = new ArrayList<>();
        chatRV = new ChatRV(chatModalArrayList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        chat.setLayoutManager(manager);
        chat.setAdapter(chatRV);
        startbot();
        MensagemEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MensagemUsuario.getText().toString().isEmpty()){
                    return;
                }
                getResponse(MensagemUsuario.getText().toString());
                MensagemUsuario.setText("");
            }
        });

        return view;
    }

    private void getResponse(String mensagem) {
        chatModalArrayList.add(new ChatModal(mensagem, USER_KEY));
        respostadobot(mensagem);
        chat.scrollToPosition(chatModalArrayList.size() - 1);
    }

    private void respostadobot(String mensagem){
        if(mensagem.length()>0) {
            chatModalArrayList.add(new ChatModal(bot.resposta(mensagem), BOT_KEY));
            if (mensagem.equals("-1")) {
                restartbot();
            }
            ArrayList<Bitmap> imagens0 = imagens();
            for(int x=0;x< imagens0.size();x++){
                chatModalArrayList.add(new ChatModal(imagens0.get(x),BOT_IMG_KEY));
            }
        } else {
            chatModalArrayList.add(new ChatModal("mensagem invalida", BOT_KEY));
        }
    }

    private void startbot (){
        chatModalArrayList.add(new ChatModal("olá usuario\n\n"+bot.resposta(""),BOT_KEY));
    }
    private void restartbot(){
        chatModalArrayList.add(new ChatModal(bot.resposta(""),BOT_KEY));
    }

    private ArrayList<Bitmap> imagens(){
        return this.bot.retornarimagens();
    }


}

