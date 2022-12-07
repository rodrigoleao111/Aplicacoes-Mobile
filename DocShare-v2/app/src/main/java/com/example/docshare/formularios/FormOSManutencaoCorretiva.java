package com.example.docshare.formularios;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;

import com.example.docshare.R;
import com.example.docshare.outros.VizualizarForm;
import com.example.docshare.metodos.CropImage;
import com.example.docshare.metodos.ImageHelper;
import com.example.docshare.usuario.TelaUsuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FormOSManutencaoCorretiva extends AppCompatActivity implements ImageHelper {

    private Button bt_visualizarOS;
    private TextInputEditText edtNome, edtRG, edtCPF, edtSetor, edtCargo, edtTelefone, edtEmail;  // Edt referente as informaçoes do colaborador
    private TextInputEditText edtEquipamento, edtModelo, edtEquipID;                              // Edt referente ao Equipamento | Ativo
    private TextInputEditText edtDiagnostico, edtSolucao, edtPecasTrocadas, edtObservacoes;       // Edt referente a manutenção
    private TextInputEditText edtDescricao;
    private TextInputLayout txtDesccricao;
    private ImageView addFoto, preview;
    private AutoCompleteTextView formLocacao;
    private ArrayAdapter<String> adapter_locacoes;
    private View vwConteiner;
    private Bitmap bitmap;
    FirebaseFirestore db_dados_usuario = FirebaseFirestore.getInstance();
    private String userID, bitmapPath = null, item_locacao;
    private String[] array_locacoes = {"Un. Recife I", "Un. Recife II", "Un. Camaragibe"};

    // Códigos de requisição
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private String[] cameraPermission;
    private String[] storagePermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_osmanutencao_corretiva);

        getSupportActionBar().hide();
        IniciarComponentes();

        // Adicionar imagem
        addFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePicDialog();
            }
        });

        // Recebendo dados de CropImage
        Intent receberProfilePic = getIntent();
        if(receberProfilePic.getBooleanExtra("check", false)){
            preview.setVisibility(View.VISIBLE);
            bitmapPath = receberProfilePic.getStringExtra("path");
            preview.setImageURI(Uri.parse(bitmapPath));
            edtDescricao.setVisibility(View.VISIBLE);
            vwConteiner.setVisibility(View.VISIBLE);
            txtDesccricao.setVisibility(View.VISIBLE);
            bitmap = BitmapFactory.decodeFile(bitmapPath);
            Bundle dados = receberProfilePic.getExtras();
            RePreenchimento(dados);
        }

        formLocacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_locacao = parent.getItemAtPosition(position).toString();}});

    }

    private void RePreenchimento(Bundle dados) {
        edtEquipamento.setText(dados.getString("equipamento"));
        edtModelo.setText(dados.getString("modelo"));
        edtEquipID.setText(dados.getString("equipID"));
        edtDiagnostico.setText(dados.getString("diagnostico"));
        edtSolucao.setText(dados.getString("solucao"));
        edtPecasTrocadas.setText(dados.getString("troca"));
        edtObservacoes.setText(dados.getString("obs"));
        formLocacao.setText(dados.getString("locacao"));
        item_locacao = dados.getString("locacao");
    }

    public void showImagePicDialog() {
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        String[] options = {"Câmera", "Galeria"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormOSManutencaoCorretiva.this);
        builder.setTitle("Selecione a fonte da imagem:");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraResultLauncher.launch(camera);
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        mGetContent.launch("image/*");
                    }
                }
            }
        });
        builder.create().show();
    }

    // Checagem de permissão: armazenamento externo
    public Boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    // Requisição de permissão: galeria
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    // Checagem de permissão: camera
    public Boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    // Requisição de permissão: camera
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    // Coletar imagem da camera
    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // ASSIM ESTOU MODIFICANDO A TUMBNAIL (ESTICANDO A IMAGEM)
                            Bitmap cameraPic = (Bitmap)(data.getExtras().get("data"));
                            Uri tempUri = ImageHelper.getUriFromTumbnailBitmap(getApplicationContext(), cameraPic);

                            Intent sendToCropImageActivity = new Intent(getApplicationContext(), CropImage.class);
                            sendToCropImageActivity.putExtra("uri", tempUri);
                            sendToCropImageActivity.putExtra("call", 1);
                            sendToCropImageActivity.putExtra("source", 1);
                            sendToCropImageActivity.putExtras(ColetarInformacoes());
                            startActivity(sendToCropImageActivity);

                        }
                    }
                }
            });

    // Coletar imagem da galeria
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    Intent sendToCropImageActivity = new Intent(getApplicationContext(), CropImage.class);
                    sendToCropImageActivity.putExtra("uri", result);
                    sendToCropImageActivity.putExtra("call", 1);
                    sendToCropImageActivity.putExtra("source", 1);
                    sendToCropImageActivity.putExtras(ColetarInformacoes());
                    startActivity(sendToCropImageActivity);
                }
            });

    @Override
    public void onBackPressed() {
        Intent backToUserScreen = new Intent(this, TelaUsuario.class);
        startActivity(backToUserScreen);
    }

    // Preenchimento de dados
    @Override
    protected void onStart() {
        super.onStart();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db_dados_usuario.collection("Usuarios").document(userID);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    edtNome.setText(documentSnapshot.getString("nome"));
                    edtRG.setText(documentSnapshot.getString("rg"));
                    edtCPF.setText(documentSnapshot.getString("cpf"));
                    edtSetor.setText(documentSnapshot.getString("setor"));
                    edtCargo.setText(documentSnapshot.getString("cargo"));
                    edtTelefone.setText(documentSnapshot.getString("telefone"));
                    edtEmail.setText(email);
                }
            }
        });

        bt_visualizarOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToVizualizationActivity = new Intent(getApplicationContext(), VizualizarForm.class);
                goToVizualizationActivity.putExtras(ColetarInformacoes());
                if(bitmap != null) {
                    goToVizualizationActivity.putExtra("BitmapImage", bitmapPath);
                }
                startActivity(goToVizualizationActivity);
            }
        });
    }

    public void IniciarComponentes() {
        edtNome = findViewById(R.id.userNametxt);
        edtRG = findViewById(R.id.userRGtxt);
        edtCPF = findViewById(R.id.userCPFtxt);
        edtSetor = findViewById(R.id.userSetortxt);
        edtCargo = findViewById(R.id.userCargotxt);
        edtTelefone = findViewById(R.id.userTelefonetxt);
        edtEmail = findViewById(R.id.userEmailtxt);

        formLocacao = findViewById(R.id.edtFormOSLocacaoipt);
        edtEquipamento = findViewById(R.id.edtFormOSEquipamentoipt);
        edtModelo =findViewById(R.id.edtFormOSModeloipt);
        edtEquipID = findViewById(R.id.edtFormOSIDEquipamentoipt);

        edtDiagnostico = findViewById(R.id.edtFormOSDiagnosticoipt);
        edtSolucao = findViewById(R.id.edtFormOSSolucaoipt);
        edtPecasTrocadas = findViewById(R.id.edtFormOSTrocaipt);
        edtObservacoes = findViewById(R.id.edtFormOSObservacoesipt);

        addFoto = findViewById(R.id.addFoto);
        preview = findViewById(R.id.preview);

        edtDescricao = findViewById(R.id.edtFormOSDescricaoipt);
        txtDesccricao = findViewById(R.id.edtFormOSDescricao);
        vwConteiner = findViewById(R.id.containerHist4);

        bt_visualizarOS = findViewById(R.id.bt_visualizarOS);

        adapter_locacoes = new ArrayAdapter<String>(this, R.layout.dropdown_item, array_locacoes);

        formLocacao.setAdapter(adapter_locacoes);
    }


    public Bundle ColetarInformacoes() {
        Bundle formularioOS = new Bundle();

        Date formID = new Date();

        formularioOS.putInt("formType", 1);     // Tipo do formulário
        formularioOS.putString("formID", String.valueOf(formID.getTime()));

        String[] chaves = {"nome", "rg", "cpf", "setor", "cargo", "telefone", "email", "equipamento",
                "modelo", "equipID", "diagnostico", "solucao", "troca", "obs", "descricaoImg"};

        EditText[] dadosDoForm = {edtNome, edtRG, edtCPF, edtSetor, edtCargo, edtTelefone, edtEmail,
                edtEquipamento, edtModelo, edtEquipID, edtDiagnostico, edtSolucao, edtPecasTrocadas, edtObservacoes, edtDescricao};

        for(int i = 0; i < chaves.length; i++) {
            formularioOS.putString(chaves[i], dadosDoForm[i].getText().toString());
        }

        formularioOS.putString("locacao", item_locacao);

        return formularioOS;
    }

}