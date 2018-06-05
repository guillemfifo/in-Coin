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

public class MainActivity extends AppCompatActivity {

    private EditText d;
    private EditText c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {

        new DownloadTask(this).execute("http://192.168.0.160:9000/Application/login" );
    }

    public void registre(View view) {
        Intent registre = new Intent(getApplicationContext(), registre.class);
        startActivity(registre);
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

           /* EditText n = (EditText) findViewById (R.id.editText);
            n.setText(result);*/
            Toast.makeText(
                    getBaseContext(),
                    result,
                    Toast.LENGTH_LONG)
                    .show();

            if (result.contains("Benvingut")) {

                Intent Principal = new Intent(getApplicationContext(), Principal.class);
                Principal.putExtra("dni",d.getText());
                startActivity(Principal);
            }


        }

    }

    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

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

        d = (EditText) findViewById (R.id.editText);
        c = (EditText) findViewById (R.id.editText2);

        String query = String.format("%s?d=%s&c=%s",urlString,d.getText().toString().trim(),c.getText().toString());

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

    public void convidat(View view) {
        Intent convidat = new Intent(getApplicationContext(), Principal.class);
        startActivity(convidat);
    }

}
