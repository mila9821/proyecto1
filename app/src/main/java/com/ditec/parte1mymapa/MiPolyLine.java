package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MiPolyLine  extends AppCompatActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {
    GoogleMap gMap;
    SeekBar seekwidth, seekazul, seekverde, seekrojo;
    Button btndibujar, btnlimpiar;
    Polyline polyline = null;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    int rojo=0,verde=0,azul=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_poly_line);

        seekwidth = findViewById(R.id.seek_width);
        seekazul = findViewById(R.id.seek_azul);
        seekverde = findViewById(R.id.seek_verde);
        seekrojo = findViewById(R.id.seek_rojo);
        btndibujar = findViewById(R.id.btndibujar);
        btnlimpiar = findViewById(R.id.btnlimpiar);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        btndibujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(latLngList).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);
                polyline.setColor(Color.rgb(rojo,verde,azul));
                int Width = seekwidth.getProgress();
                polyline.setWidth(Width);
            }
        });
        btnlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polyline != null) polyline.remove();
                for (Marker marker : markerList) marker.remove();
                latLngList.clear();
                markerList.clear();
                seekwidth.setProgress(3);
                seekazul.setProgress(0);
                seekrojo.setProgress(0);
                seekverde.setProgress(0);
            }
        });
        seekazul.setOnSeekBarChangeListener(this);
        seekrojo.setOnSeekBarChangeListener(this);
        seekverde.setOnSeekBarChangeListener(this);
        seekwidth.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = gMap.addMarker(markerOptions);
                latLngList.add(latLng);
                markerList.add(marker);
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()){
            case R.id.seek_rojo:
                rojo=i;
                break;
            case R.id.seek_verde:
                verde=i;
                break;
            case R.id.seek_azul:
                azul=i;
                break;
            case R.id.seek_width:
                int Width = seekwidth.getProgress();
                if(polyline!=null)
                    polyline.setWidth(Width);
                break;
        }
        if(polyline!=null)
            polyline.setColor(Color.rgb(rojo,verde,azul));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}