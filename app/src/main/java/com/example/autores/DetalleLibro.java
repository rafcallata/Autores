package com.example.autores;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleLibro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        String titulo ="";
        String autor ="";
        String anio ="";
        String descripcion ="";
        String imagenportada="";

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            titulo = extras.getString("titulo");
            autor = extras.getString("autor");
            anio = extras.getString("anio");
            descripcion = extras.getString("descripcion");
            imagenportada =extras.getString("imagenlibro");
        }

        TextView txttitulo = (TextView)findViewById(R.id.txttitulo);
        TextView txtautor= (TextView)findViewById(R.id.txtautor);
        TextView txtanio = (TextView)findViewById(R.id.txtanio);
        TextView txtdescripcion = (TextView)findViewById(R.id.txtdescripcion);
        ImageView imageView=(ImageView)findViewById(R.id.imgLibro);

        txttitulo.setText("Titulo: "+titulo);
        txtautor.setText("Autor: "+autor);
        txtanio.setText("Fecha: "+anio);
        txtdescripcion.setText(descripcion);
    }
}
