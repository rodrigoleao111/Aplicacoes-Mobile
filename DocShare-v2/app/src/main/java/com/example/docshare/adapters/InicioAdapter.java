package com.example.docshare.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.docshare.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InicioAdapter extends RecyclerView.Adapter<InicioAdapter.MyviewHolder>{

    private Context context;
    private List<File> pdfFiles;

    public InicioAdapter(Context context, List<File> pdfFiles) {
        this.context = context;
        this.pdfFiles = pdfFiles;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_recycler_inicio, parent, false);
        return new InicioAdapter.MyviewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtTitulo.setText(pdfFiles.get(position).getName());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(pdfFiles.get(position).lastModified());
        holder.txtData.setText(formatter.format(date));

    }

    @Override
    public int getItemCount() {return pdfFiles.size();}

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo;
        TextView txtData;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.textViewTituloInicio);
            txtData = itemView.findViewById(R.id.textViewData);
        }
    }
}