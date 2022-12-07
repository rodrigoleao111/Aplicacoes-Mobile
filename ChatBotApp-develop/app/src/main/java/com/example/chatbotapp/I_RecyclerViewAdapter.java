package com.example.chatbotapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class I_RecyclerViewAdapter extends RecyclerView.Adapter<I_RecyclerViewAdapter.MyViewHolder>{
    private final I_RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<String> listatxt;

    public I_RecyclerViewAdapter(Context context, ArrayList<String> listatxt,
                                 I_RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.listatxt=listatxt;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public I_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.arecycler_view_linha,parent,false);
        return new I_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull I_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(listatxt.get(position));
        // chechar se tem icone
        if(possuiIcone(position,".jpg")){
            holder.imageView.setImageBitmap(pegaIcone(position,".jpg"));
        } else if (possuiIcone(position,".png")) {
            holder.imageView.setImageBitmap(pegaIcone(position,".png"));
        }

    }

    public boolean possuiIcone(int position, String tipo){
        String pathpasta = context.getExternalFilesDir(null).getPath()+"/P2Z_"+listatxt.get(position);
        String[] listagemdapasta = new File(pathpasta).list();
        for(int x=0;x<listagemdapasta.length;x++){
            if(listagemdapasta[x].contains("icone"+tipo)){
                return true;
            }
        }

        return false;
    }

    public Bitmap pegaIcone(int position,String tipo){
        String pathicone = context.getExternalFilesDir(null).getPath()+
                "/P2Z_"+listatxt.get(position)
                +"/"+"icone"+tipo;

        Bitmap bitmap = BitmapFactory.decodeFile(pathicone);
        return bitmap;
    }


    @Override
    public int getItemCount() {
        return listatxt.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView, I_RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            
            imageView = itemView.findViewById(R.id.imageView5);
            textView = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position = getAdapterPosition();
                        
                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
