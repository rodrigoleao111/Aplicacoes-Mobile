package com.example.docshare.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docshare.adapters.InicioAdapter;
import com.example.docshare.R;
import com.example.docshare.formularios.FormOSManutencaoCorretiva;
import com.example.docshare.metodos.ImageHelper;
import com.example.docshare.metodos.RequestPermissions;
import com.example.docshare.metodos.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class InicioFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private TextView boasvindas;
    private ImageView profilePic;
    private Button button_novaOS;
    private RecyclerView recyclerView;
    private List<File> pdfList;
    Bundle paths = new Bundle();
    FirebaseFirestore db_dados_usuario = FirebaseFirestore.getInstance();
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid(), ola;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        IniciarComponentes(view);

        VerificacaoDiretoriosDoApp(getContext());

        InicioAdapter adapter = new InicioAdapter(getContext(), pdfList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        button_novaOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RequestPermissions.checkPermission(getContext())) {
                    Intent goToFormOsActivity = new Intent(getContext(), FormOSManutencaoCorretiva.class);
                    goToFormOsActivity.putExtras(paths);
                    startActivity(goToFormOsActivity);
                } else {
                    Toast.makeText(getContext(),"permissão negada", Toast.LENGTH_LONG).show();
                    RequestPermissions.requestPermission(getActivity());
                }
            }
        });

        return view;
    }

    private void VerificacaoDiretoriosDoApp(Context context) {
        if (RequestPermissions.checkPermission(context)) {
            String rootDirName = "DocShare", imagesDirName = "DocShare_images", osDirName = "DocShare_os_files";
            File rootDirFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), rootDirName);
            rootDirFile.mkdir();
            File userDir = new File(rootDirFile, userID);
            userDir.mkdir();
            File imageDir = new File(userDir, imagesDirName);
            imageDir.mkdir();
            File osDir = new File(userDir, osDirName);
            osDir.mkdir();

            UserInfo.setUserPaths(
                    rootDirFile.getAbsolutePath(),
                    userDir.getAbsolutePath(),
                    imageDir.getAbsolutePath(),
                    osDir.getAbsolutePath());

            displayPdf();

        } else {
            RequestPermissions.requestPermission(getActivity());
        }
    }

    private void IniciarComponentes(View view) {
        boasvindas = view.findViewById(R.id.txt_boas_vindas2);
        profilePic = view.findViewById(R.id.profilePicInit);
        button_novaOS = view.findViewById(R.id.button_novaOS);
        recyclerView = view.findViewById(R.id.recyclerInicio);
    }

    public ArrayList<File> findPdf (File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        assert files != null;
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                arrayList.addAll(findPdf(singleFile));
            } else {
                if (singleFile.getName().endsWith(".pdf")) {
                    arrayList.add(0, singleFile);
                }
            }

        }
        return arrayList;
    }

    private void displayPdf() {
        pdfList = new ArrayList<>();
        File diretorio = new File(UserInfo.getUserCredentials().getString("osPath"));
        pdfList.addAll(findPdf(diretorio));
    }

    @Override
    public void onStart() {
        super.onStart();

        DocumentReference documentReference = db_dados_usuario.collection("Usuarios").document(userID);

        // Texto de Boas Vindas
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    String[] nomeUser = documentSnapshot.getString("nome").split("\\s");
                    ola = "Olá, " + nomeUser[0];
                    boasvindas.setText(ola);
                    if(!Objects.equals(documentSnapshot.getString("profilePicUri"), "void")) {
                        Bitmap profilePicBitmap = BitmapFactory.decodeFile(documentSnapshot.getString("profilePicUri"));
                        if(profilePicBitmap !=null)
                            profilePic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(profilePicBitmap, profilePicBitmap.getHeight()));
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(getContext(), "Permission Granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission Denined.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}