package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;
import java.util.List;

public class MapaCalor  extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    GoogleMap mapa; List<Marker> markerList = new ArrayList<>();
    TileOverlay mOverlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_calor);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-18.013766, -70.255331), 15));
        mapa.setOnMapClickListener(this);
        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Marker mimarker : markerList)
                    if (mimarker.equals(marker)) {
                        markerList.remove(mimarker);
                        break;
                    }
                marker.remove();
                return true;
            }
        });
        mapa.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mapa.getCameraPosition();
                float zoom = position.zoom;
                for(Marker marker:markerList)
                    marker.setVisible(zoom>=12);
            }
        });
    }
    @Override
    public void onMapClick(LatLng latLng) {
        Marker Mymarker = mapa.addMarker(new MarkerOptions()
                .position(latLng)
        );
        markerList.add(Mymarker);
    }
    public void mapacalor(View view) {
// Create the gradient with whatever start and end colors you wish to use
        int[] colors = {Color.BLUE, Color.parseColor("#EA9F32")};
        float[] startPoints = {0.2f, 1f};
        Gradient gradient = new Gradient(colors, startPoints);
        if(!markerList.isEmpty()) {
            HeatmapTileProvider mProvider;
            ArrayList<LatLng> lista = new ArrayList<>();
            for (Marker marker : markerList)
                lista.add(marker.getPosition());
            mProvider = new HeatmapTileProvider.Builder()
                    .data(lista)
            .radius(50) // de 10 a 50
            .maxIntensity(1) //intensidad segun numero de datos
.opacity(0.5) //opacidad por defecto
.gradient(gradient) //degradacion entre colores
                    .build();
            mOverlay = mapa.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
        }
    }

    public void limpiar(View view) {
        if (mOverlay != null) mOverlay.remove();
        for (Marker marker : markerList) marker.remove();
        markerList.clear();
    }
}