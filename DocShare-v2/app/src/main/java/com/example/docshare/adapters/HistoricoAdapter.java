package com.example.docshare.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docshare.R;
import com.example.docshare.metodos.OnPdfFileSelectListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.MyviewHolder> {

    private Context context;
    private List<File> pdfFiles;
    private OnPdfFileSelectListener listener;


    public HistoricoAdapter(Context context, List<File> pdfFiles, OnPdfFileSelectListener listener) {
        this.context = context;
        this.pdfFiles = pdfFiles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_listview_custom, parent, false);
        return new MyviewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtTitulo.setText(pdfFiles.get(position).getName());
        holder.txtTitulo.setSelected(true);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(pdfFiles.get(position).lastModified());
        holder.txtdata.setText(formatter.format(date));

        holder.conteiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPdfSelected(pdfFiles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {return pdfFiles.size();}

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtdata;
        ConstraintLayout conteiner;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.tituloHistorico);
            txtdata = itemView.findViewById(R.id.txtData);
            conteiner = itemView.findViewById(R.id.line_historico);
        }
    }
}