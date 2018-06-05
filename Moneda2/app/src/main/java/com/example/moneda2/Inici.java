package com.example.moneda2;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;


public class Inici extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private LinearLayout mainLayout;
    private ListView menuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inici);

        ActionBar aBar = getSupportActionBar();
        aBar.setHomeButtonEnabled(true);
        aBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        menuLateral = (ListView) findViewById(R.id.menuLateral);

        String[] opciones = {"Opció 1", "Opció 2", "Opció 3", "Opció 4", "Opció 5"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(Inici.this, android.R.layout.simple_list_item_1, opciones);
        menuLateral.setAdapter(adp);


    }


    public boolean onOptionItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(menuLateral)) {
                drawerLayout.closeDrawer(menuLateral);
            } else {
                drawerLayout.openDrawer(menuLateral);
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
