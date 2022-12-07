package com.example.docshare.metodos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.example.docshare.R;
import com.example.docshare.formularios.FormOSManutencaoCorretiva;
import com.example.docshare.usuario.ConfiguracoesDeUsuario;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/***
 * Função da classe: Cortar imagem recebida na proporção 1:1
 *
 * Parâmetros:
 *      - [Intent Recebedora]  ParcelableExtra "uri" ou  "byteArray", referente a imagem a ser cortada
 *      - [Intent Recebedora] Extra "call", inteiro referente a classe que chamou
 *              - ConfiguracoesDeUsuario: 0 | FormOSManutencao: 1
*       - [Intent Recebedora] Extra "source", inteiro referente a fonte da imagem
 *              - Camera: 0 | Galeria: 1
 *
 *      - [Intent Retorno] ParcelableExtra "uri", referente a imagem cortada
 *      - [Intent Retorno] Extra "check" boolean, referente a finalização do processo
 */
public class CropImage extends AppCompatActivity {

    private static final int NO_CONTENT = 404;

    private ImageView btRotate, btCancel, btCrop;
    private ProgressBar progressBarCrop;
    private CropImageView mCropImageView;
    float rotation = 0.0f;
    int call, source;
    Matrix matrix = new Matrix();
    Bitmap bitmap, finalBitmap;
    Bundle dadosDoForm = new Bundle();

    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // DIRETÓRIOS DO APP
    Bundle paths = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);

        getSupportActionBar().hide();
        IniciarComponentes();
        progressBarCrop.setVisibility(ProgressBar.INVISIBLE);

        // Recebendo intent
        Intent intentReceberForm = getIntent();
        paths = intentReceberForm.getExtras();
        call = intentReceberForm.getIntExtra("call", NO_CONTENT);
        source = intentReceberForm.getIntExtra("source", NO_CONTENT);

        if(call == 1)
            dadosDoForm = intentReceberForm.getExtras();

        // Atribuindo bitmap
        if(source == 0){    // Camera
            byte[] bitmapdata = intentReceberForm.getByteArrayExtra("byteArray");
            bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        } else if(source==1){       // Galeria
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                        intentReceberForm.getParcelableExtra("uri"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Falha no recebimento da imagem", Toast.LENGTH_SHORT).show();
        }


        // Inicialização da imagem
        mCropImageView.setImageBitmap(bitmap);
        mCropImageView.setRotation(rotation);

        // Rotacionar imagem
        btRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation -= 90.0f;
                mCropImageView.setRotation(rotation);

            }
        });

        // Enviar imagem cortada
        btCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCrop.setVisibility(ProgressBar.VISIBLE);

                // Criar nova imagem
                matrix.setRotate(mCropImageView.getRotation());
                finalBitmap = Bitmap.createScaledBitmap(
                        mCropImageView.getCroppedImage(),
                        mCropImageView.getCroppedImage().getWidth(),
                        mCropImageView.getCroppedImage().getHeight(),
                        false);

                finalBitmap = Bitmap.createBitmap(finalBitmap, 0, 0, finalBitmap.getWidth(),
                        finalBitmap.getHeight(), matrix, false);

                // Criar arquivo de imagem de perfil
                java.util.Date date = new Date();
                String nomeArquivo = "DocShare-Image-" + userID + "-" + date.getTime() + ".png";
                File file = new File(UserInfo.getUserCredentials().getString("imagesPath"), nomeArquivo);

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Converter bitmap para byte array
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                finalBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();

                // Escrevendo no arquivo
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    Toast.makeText(getApplicationContext(), "Imagem salva com sucesso", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Voltar para tela de perfil enviando a uri da imagem
                String path = file.getAbsolutePath();
                Intent goToCropActivity = null;
                if(call == 0){
                    goToCropActivity = new Intent(getApplicationContext(), ConfiguracoesDeUsuario.class);
                } else if(call == 1){
                    goToCropActivity = new Intent(getApplicationContext(), FormOSManutencaoCorretiva.class);
                    goToCropActivity.putExtras(dadosDoForm);
                } else {
                    Toast.makeText(getApplicationContext(), "Falha no recebimento do contexto", Toast.LENGTH_SHORT).show();
                }

                goToCropActivity.putExtra("path", path);
                goToCropActivity.putExtra("check", true);
                startActivity(goToCropActivity);

            }
        });

        // Cancelar processo
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void IniciarComponentes() {
        mCropImageView = findViewById(R.id.mCropImageView);
        btCrop = findViewById(R.id.btCrop);
        btCancel = findViewById(R.id.btCancel);
        btRotate = findViewById(R.id.btRotate);
        progressBarCrop = findViewById(R.id.progressBarCrop);
    }
}