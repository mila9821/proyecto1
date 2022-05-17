package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mimapa(View view) {
        startActivity(new Intent(this,MiMapa.class));
    }

    public void mipolyline(View view) {
        startActivity(new Intent(this,MiPolyLine.class));
    }

    public void mipolygon(View view) {
        startActivity(new Intent(this,MiPolygon.class));
    }

    public void mapacalor(View view) {
        startActivity(new Intent(this,MapaCalor.class));
    }

    public void miclustermap(View view) {
        startActivity(new Intent(this,MiClusterMap.class));
    }

    public void dlineal(View view) {
        startActivity(new Intent(this,DistanciaDosPuntos.class));
    }
}