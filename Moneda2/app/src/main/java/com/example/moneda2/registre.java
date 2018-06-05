package com.example.moneda2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class registre extends AppCompatActivity {

    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registre);

    }

    public void registrar(View view){

        new DownloadTask(this).execute("http://192.168.0.160:9000/Application/registre" );

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        Context context;

        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                return loadFromNetwork(urls[0]);
            } catch (IOException e) {
                return e.getMessage();
                //return getString(R.string.connection_error);
            }
        }


        @Override
        protected void onPostExecute(String result) {

            /*EditText n = (EditText) findViewById (R.id.editText3);
            n.setText(result);*/

            Toast.makeText(
                    getBaseContext(),
                    result,
                    Toast.LENGTH_LONG)
                    .show();

            if (result.contains("Registrat")) {

                Intent MainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(MainActivity);

            }
        }

        private String loadFromNetwork(String urlString) throws IOException {
            InputStream stream = null;
            String str = "";

            try {
                stream = downloadUrl(urlString);
                str = readIt(stream, 10);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
            return str;
        }

        private InputStream downloadUrl(String urlString) throws IOException {

            EditText n = (EditText) findViewById(R.id.editText3);
            EditText c = (EditText) findViewById(R.id.editText4);
            EditText d = (EditText) findViewById(R.id.editText5);
            EditText e = (EditText) findViewById(R.id.editText6);
            EditText p = (EditText) findViewById(R.id.editText7);

            String query = String.format("%s?n=%s&c=%s&d=%s&e=%s&p=%s", urlString, n.getText().toString().trim(), c.getText().toString().trim(), d.getText().toString().trim(), e.getText().toString().trim(), p.getText().toString());

            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();


            InputStream stream = conn.getInputStream();
            return stream;

        }

        private String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

    }



}
