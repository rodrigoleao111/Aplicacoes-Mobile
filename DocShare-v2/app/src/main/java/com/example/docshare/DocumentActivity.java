package com.example.docshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.docshare.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class DocumentActivity extends AppCompatActivity {

    String filePath = "";
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        getSupportActionBar().hide();

        imageButton = findViewById(R.id.imageButton);

        PDFView pdfView = findViewById(R.id.pdfView);
        filePath = getIntent().getStringExtra("path");

        File file = new File(filePath);
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path).load();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri pathUri = FileProvider.getUriForFile(
                        getApplicationContext(),
                        "com.example.docshare.provider",
                        file);

                if(file.exists()){
                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                    intentShare.setType("application/pdf");
                    intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse(pathUri.toString()));
                    startActivity(Intent.createChooser(intentShare, "Compartilhar Ordem de Serviço"));
                } else {
                    Toast.makeText(getApplicationContext(), "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}