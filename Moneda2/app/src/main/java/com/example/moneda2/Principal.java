package com.example.moneda2;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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


public class Principal extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private LinearLayout mainLayout;
    private ListView menuLateral;
    private String d;
    private TextView textView;
    private List<Opcions> lo = new ArrayList<Opcions>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        //textView= (TextView) findViewById(R.id.textView11);
        //Bundle b = getIntent().getExtras();
        //String dni = b.getString("dni");
        //Agrega valor a TextView.
        //textView.setText(dni);
        //dadesactivity();
        opcions();
        opcionsView();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        menuLateral = (ListView) findViewById(R.id.menuLateral);

        new DownloadTask(this).execute("http://192.168.0.160:9000/Application/llistaUsuaris");
    }

    private void opcions(){
        lo.add(new Opcions("Perfil", R.mipmap.ic_launcher_foreground));
        lo.add(new Opcions("Moneder", R.mipmap.icono_moneda));
        lo.add(new Opcions("Historial", R.mipmap.icono_llibre));
        lo.add(new Opcions("Serveis", R.mipmap.icono_serveis));

    }

    private void opcionsView(){

        ArrayAdapter<Opcions> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.menuLateral);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int posicion, long id) {
                        /*if (posicion == 0) {
                            Intent perfil = new Intent(getApplicationContext(), Perfil.class);
                            startActivity(perfil);
                        }
                        else if (posicion == 1) {

                            Intent principal = new Intent(getApplicationContext(), Principal.class);
                            startActivity(principal);
                        }
                        else if (posicion == 2) {
                            Intent historial = new Intent(getApplicationContext(), Historial.class);
                            startActivity(historial);

                        }
                        else if (posicion == 3){
                            Intent serveis = new Intent(getApplicationContext(), Serveis.class);
                            startActivity(serveis);
                        }*/

                        Intent serveris = new Intent(getApplicationContext(), Serveis.class);
                        startActivityForResult(serveris, 100);
                    }
        });
    }

    public void prova(View view) {
        Intent prova = new Intent(getApplicationContext(), Serveis.class);
        startActivity(prova);
    }

    private class MyListAdapter extends ArrayAdapter<Opcions>{
        public MyListAdapter(){
            super(Principal.this, R.layout.item_view, lo);
        }

        public View getView(int posicion, View convertView, ViewGroup paret){
            View itemView = convertView;

            if (itemView==null)
                itemView=getLayoutInflater().inflate(R.layout.item_view, paret, false);

                Opcions CurrentPrincipal = lo.get(posicion);


                ImageView imageView = (ImageView)itemView.findViewById(R.id.imageView3);
                imageView.setImageResource(CurrentPrincipal.getLogo());

                TextView textView=(TextView) itemView.findViewById(R.id.textView_opcio);
                textView.setText(CurrentPrincipal.getOpcio());

                return itemView;

        }
    }

    private void dadesactivity(){
        Bundle dades = getIntent().getExtras();
        d = dades.getString("dni");
    }


    public void Menu(View view){

        if (drawerLayout.isDrawerOpen(menuLateral)) {
            drawerLayout.closeDrawer(menuLateral);
        } else {
            drawerLayout.openDrawer(menuLateral);
        }
    }

    public List<Usuari> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader((new InputStreamReader(in,"UTF-8")));
        try {
            return lletgirArrayUsuaris(reader);
        }finally {
            reader.close();
        }
    }

    public List lletgirArrayUsuaris(JsonReader reader) throws IOException{
        //Llista temporal
        ArrayList usuaris = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()){
            //Leer objeto
            usuaris.add(lletgirUsuari(reader));
        }
        reader.endArray();
        return usuaris;
    }

    public Usuari lletgirUsuari(JsonReader reader) throws IOException{
        String nom = null;
        String cognom = null;
        String dni = null;
        String correu = null;
        String contrasenya = null;
        String saldo = null;

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            switch (name){
                case "nom":
                    nom = reader.nextString();
                    break;
                case "cognom":
                    cognom = reader.nextString();
                    break;
                case "dni":
                    dni = reader.nextString();
                    break;
                case "correu":
                    correu = reader.nextString();
                    break;
                case "contrasenya":
                    contrasenya = reader.nextString();
                    break;
                case "saldo":
                    saldo = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new Usuari(nom,cognom,dni,correu,contrasenya,saldo);
    }

    private class DownloadTask extends AsyncTask<String, Void, List<Usuari>> {
        Context context;
        InputStream stream = null;
        String str = "";

        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Usuari> doInBackground(String... urls) {


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
                List<Usuari> lu = Arrays.asList(gson.fromJson(result, Usuari[].class));
                //poiObject p = gson.fromJson(result, poiObject.class);
                /*Productos l = gson.fromJson(result, Productos[].class);*/
                /*Log.i("lolaforms1", lp.lProductos.get(0).autor);*/

                return lu;


            } catch (IOException e) {
                return null;
            }
        }

        @Override


        protected void onPostExecute(List<Usuari> result) {

            ListView milista = (ListView) findViewById(R.id.listView_usuaris);
            if (result != null) {
                AdaptadorDeUsuaris adaptador = new AdaptadorDeUsuaris(getBaseContext(), result);
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
