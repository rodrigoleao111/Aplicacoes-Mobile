package com.example.docshare.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.docshare.adapters.HistoricoAdapter;
import com.example.docshare.R;
import com.example.docshare.DocumentActivity;
import com.example.docshare.metodos.OnPdfFileSelectListener;
import com.example.docshare.metodos.RequestPermissions;
import com.example.docshare.metodos.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoricoFragment extends Fragment implements OnPdfFileSelectListener {

    private RecyclerView recyclerView;
    private List<File> pdfList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        permissoes();
        recyclerView = view.findViewById(R.id.recyclerView);
        HistoricoAdapter adapter = new HistoricoAdapter(getContext(), pdfList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration( new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void permissoes() {
        if(RequestPermissions.checkPermission(getActivity())){
            displayPdf();
        } else {
            RequestPermissions.requestPermission(getActivity());
        }
    }

    public ArrayList<File> findPdf (File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        assert files != null;
        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findPdf(singleFile));
            } else {
                if (singleFile.getName().endsWith(".pdf")){
                    arrayList.add(0,singleFile);
                }
            }
        } return arrayList;
    }


    private void displayPdf() {

        pdfList = new ArrayList<>();
        File diretorio = new File(UserInfo.getUserCredentials().getString("osPath"));
        pdfList.addAll(findPdf(diretorio));

    }

    @Override
    public void onPdfSelected(File file) {
        startActivity( new Intent(getContext(), DocumentActivity.class).putExtra("path", file.getAbsolutePath()));
    }
}