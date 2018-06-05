package com.example.moneda2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serveis extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serveis);

        new DownloadTask(this).execute("http://192.168.0.160:9000/Application/llistaServeis");

    }
    public List<Servei> readJsonStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader((new InputStreamReader(in,"UTF-8")));
        try {
            return lletgirArrayServeis(reader);
        }finally {
            reader.close();
        }
    }

    public List lletgirArrayServeis(JsonReader reader) throws IOException{
        //Llista temporal
        ArrayList serveis = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()){
            //Leer objeto
            serveis.add(lletgirServei(reader));
        }
        reader.endArray();
        return serveis;
    }

    public Servei lletgirServei(JsonReader reader) throws IOException{
        String servei = null;
        String horari = null;
        String telefon = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "servei":
                    servei = reader.nextString();
                    break;
                case "horari":
                    horari = reader.nextString();
                    break;
                case "telefon":
                    telefon = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Servei(servei,horari,telefon);
    }

    private class DownloadTask extends AsyncTask<String, Void, List<Servei>> {
        Context context;
        InputStream stream = null;
        String str = "";

        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Servei> doInBackground(String... urls) {


            try {

                String query = String.format(urls[0]);
                URL url = new URL(query);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();

                BufferedReader reader = null;
                String result = null;
                StringBuilder sb = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(stream));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();


                /*Log.i("lolaforms1", result);*/
                /*result = "{\"lProductos\":" + result + "}";*/

                GsonBuilder builder = new GsonBuilder();

                Gson gson = builder.create();
                List<Servei> ls = Arrays.asList(gson.fromJson(result, Servei[].class));
                //poiObject p = gson.fromJson(result, poiObject.class);
                /*Productos l = gson.fromJson(result, Productos[].class);*/
                /*Log.i("lolaforms1", lp.lProductos.get(0).autor);*/

                return ls;


            } catch (IOException e) {
                return null;
            }
        }

        @Override


        protected void onPostExecute(List<Servei> result) {

            ListView milista = (ListView) findViewById(R.id.listView_serveis);
            if (result != null) {
                AdaptadorDeServeis adaptador = new AdaptadorDeServeis(getBaseContext(), result);
                milista.setAdapter(adaptador);
            } else {
                Toast.makeText(
                        getBaseContext(),
                        "Ocurrió un error de Parsing Json",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            /*milista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String nombredisco = ((TextView) view.findViewById(R.id.textView_nombredisco)).getText().toString();
                    String autor = ((TextView) view.findViewById(R.id.textView_autor)).getText().toString();
                    Intent comprar = new Intent(getApplicationContext(), Comprar.class);
                    comprar.putExtra("nombredisco",nombredisco);
                    comprar.putExtra("autor",autor);
                    startActivityForResult(comprar,100);
                }
            });*/



            /*productosArray= new ArrayList();
            productosArray.add(result);


            AdapterProductos = new AdapterProductos(Electronica.this, R.layout.activity_itemlista, productosArray);

            milista.setAdapter(adaptador);*/
        }
    }
}