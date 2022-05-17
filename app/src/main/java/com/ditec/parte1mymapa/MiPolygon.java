package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class MiPolygon  extends AppCompatActivity implements OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {
    GoogleMap gMap;
    CheckBox checkpintar;
    SeekBar seekazul, seekverde, seekrojo;
    Button btndibujar, btnlimpiar;
    Polygon polygon= null;
    List<LatLng> latLngList= new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    int rojo=0,verde=0,azul=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_polygon);

        checkpintar=findViewById(R.id.checkpolygon);
        seekazul=findViewById(R.id.seek_azul);
        seekverde=findViewById(R.id.seek_verde);
        seekrojo=findViewById(R.id.seek_rojo);
        btndibujar=findViewById(R.id.btndibujar);
        btnlimpiar=findViewById(R.id.btnlimpiar);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        checkpintar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!latLngList.isEmpty()) {
                    if (b) {
                        if (polygon == null) return;
                        polygon.setFillColor(Color.rgb(rojo, verde, azul));
                    } else {
                        polygon.setFillColor(Color.TRANSPARENT);
                    }
                }
            }
        });
        btndibujar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon != null) polygon.remove();
                if(!latLngList.isEmpty()) {
                    PolygonOptions polygonOptions = new PolygonOptions()
                            .addAll(latLngList).clickable(true);
                    polygon = gMap.addPolygon(polygonOptions);
                    polygon.setStrokeColor(Color.rgb(rojo, verde, azul));
                    if (checkpintar.isChecked())
                        polygon.setFillColor(Color.rgb(rojo, verde, azul));
                    Log.i("miarea", SphericalUtil.computeArea(latLngList)+"");
                }
            }
        });
        btnlimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(polygon!=null) polygon.remove();
                for(Marker marker: markerList) marker.remove();
                latLngList.clear();
                markerList.clear();
                seekazul.setProgress(0);
                seekrojo.setProgress(0);
                seekverde.setProgress(0);
            }
        });
        seekazul.setOnSeekBarChangeListener(this);
        seekrojo.setOnSeekBarChangeListener(this);
        seekverde.setOnSeekBarChangeListener(this);
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
        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)
                        .radius(300000);//En metros
                Circle circle=gMap.addCircle(circleOptions);
                circle.setStrokeWidth(8);
            }
        });
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.seek_rojo:
                rojo = i;
                break;
            case R.id.seek_verde:
                verde = i;
                break;
            case R.id.seek_azul:
                azul = i;
                break;
        }
        if (polygon != null) {
            polygon.setStrokeColor(Color.rgb(rojo, verde, azul));
            if (checkpintar.isChecked())
                polygon.setFillColor(Color.rgb(rojo, verde, azul));
        }
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}