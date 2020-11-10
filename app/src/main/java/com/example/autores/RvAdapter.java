package com.example.autores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.LibroViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;
    List<Ent_Libro> mLibro;
    Context context;
    RvAdapter(List<Ent_Libro> libros, Context context){
        this.mLibro = libros;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RvAdapter.LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.libro,parent,false);
        LibroViewHolder lvh = new LibroViewHolder(view);

        view.setOnClickListener(this);

        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.LibroViewHolder holder, int position) {

        holder.tituloLibro.setText(mLibro.get(position).titulo);
        holder.autorlibro.setText(mLibro.get(position).autor);
        holder.anioLibro.setText(mLibro.get(position).anio);
        holder.descripcionLibro.setText(mLibro.get(position).descripcion);
        if (mLibro.get(position).imagenlibro.equals("")||mLibro.get(position).imagenlibro==null){
            holder.fotoLibro.setImageResource(R.drawable.imagenerror);
        }
        else {
            Picasso.with(context).load(mLibro.get(position).imagenlibro).into(holder.fotoLibro);
        }
        //holder.cv.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //   public void onClick(View v) {
        //       if(listener != null){
        //           listener.onClick(v);
        //        }
        //    }
        //});
    }

    @Override
    public int getItemCount() {
        return mLibro.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tituloLibro;
        TextView autorlibro;
        TextView anioLibro;
        TextView descripcionLibro;
        ImageView fotoLibro;

        LibroViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvLibro);
            tituloLibro = (TextView)itemView.findViewById(R.id.txtTituloLibro);
            autorlibro = (TextView)itemView.findViewById(R.id.txtAutorLibro);
            anioLibro = (TextView)itemView.findViewById(R.id.txtAnioLibro);
            descripcionLibro = (TextView)itemView.findViewById(R.id.txtDescripcionLibro);
            fotoLibro=(ImageView)itemView.findViewById(R.id.imgLibro);
        }
    }
}
