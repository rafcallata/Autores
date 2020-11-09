package com.example.autores;

import androidx.appcompat.app.AppCompatActivity;

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
        mTextoTitulo = (TextView)findViewById(R.id.titulo);
        mTextoAutor = (TextView)findViewById(R.id.autorLibro);
    }
    public void buscarLibro(View view){
        if(mInputLibro.getText().toString().equals("")){
            mlibro.clear();
        }
        else {
            String cadenaBusqueda = mInputLibro.getText().toString();
            new ConseguirLibro(mTextoTitulo, mTextoAutor, mTextoDescripcion, mTextoanio).execute(cadenaBusqueda);
            super.onRestart();
        }
    }

    public class ConseguirLibro extends AsyncTask<String,Void,String> {

        private WeakReference<TextView> mTextoTitulo;
        private WeakReference<TextView> mTextoAutor;
        private WeakReference<TextView> mTextoDescripcion;
        private WeakReference<TextView> mTextoanio;

        ConseguirLibro(TextView tituloTexto, TextView autorTexto,TextView descripcionTexto, TextView anioTexto){
            this.mTextoTitulo = new WeakReference<>(tituloTexto);
            this.mTextoAutor = new WeakReference<>(autorTexto);
            this.mTextoDescripcion = new WeakReference<>(descripcionTexto);
            this.mTextoanio = new WeakReference<>(anioTexto);
        }

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
                ImageView imagen=(ImageView)findViewById(R.id.imagenlibro);
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
                        Picasso.with(MainActivity.this).load(link).into(imagen);
                        mlibro.add(new Ent_Libro(titulo,autores,descripcion,anio));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    i++;
                    if(titulo != null && autores != null){
                        mTextoTitulo.get().setText(titulo);
                        mTextoAutor.get().setText(autores);
                    }else{
                        mTextoTitulo.get().setText("No existen resultados para la consulta");
                        mTextoAutor.get().setText("");
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

        }
    }

}