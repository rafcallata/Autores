package com.example.autores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mInputLibro;
    private TextView mTextoTitulo;
    private TextView mTextoAutor;
    private TextView mTextoDescripcion;
    private TextView mTextoanio;
    private List<Ent_Libro> mlibro;

    private void contruirList() {
        mlibro = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInputLibro = (EditText)findViewById(R.id.ingresoLibro);
        contruirList();

    }
    public void buscarLibro(View view){
        if(mInputLibro.getText().toString().equals("")){
            mlibro.clear();
        }
        else {
            String cadenaBusqueda = mInputLibro.getText().toString();
            new ConseguirLibro().execute(cadenaBusqueda);
            super.onRestart();
        }
    }

    public class ConseguirLibro extends AsyncTask<String,Void,String> {



        @Override
        protected String doInBackground(String... strings) {
            return NetUtilities.getBookInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                int i= 0;
                String titulo = null;
                String autores = null;
                String link = null;
                String anio = null;
                String descripcion = null;

                mlibro.clear();
                while(i < itemsArray.length()){
                    JSONObject libro = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                    JSONObject imagenlinks = volumeInfo.getJSONObject("imageLinks");
                    try{
                        titulo = volumeInfo.getString("title");
                        autores = volumeInfo.getString("authors");
                        descripcion = volumeInfo.getString("description");
                        anio = volumeInfo.getString("publishedDate");
                        link=imagenlinks.getString("smallThumbnail");
                        link=link.replace("http","https");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    i++;
                    if(titulo != null && autores != null){
                        mlibro.add(new Ent_Libro(titulo,autores,descripcion,anio,link));
                    }else{
                        mlibro.add(new Ent_Libro("","","","",""));
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RvLibros);
            recyclerView.setHasFixedSize(true);


            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);

            RvAdapter rvAdapter= new RvAdapter(mlibro,getApplicationContext());

            rvAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DetalleLibro.class);
                    intent.putExtra("titulo", mlibro.get(recyclerView.getChildAdapterPosition(v)).titulo);
                    intent.putExtra("autor", mlibro.get(recyclerView.getChildAdapterPosition(v)).autor);
                    intent.putExtra("anio", mlibro.get(recyclerView.getChildAdapterPosition(v)).anio);
                    intent.putExtra("descripcion", mlibro.get(recyclerView.getChildAdapterPosition(v)).descripcion);
                    intent.putExtra("imagenlibro",mlibro.get(recyclerView.getChildAdapterPosition(v)).imagenlibro);
                    startActivity(intent);
                }
            });


            recyclerView.setAdapter(rvAdapter);

        }
    }

}