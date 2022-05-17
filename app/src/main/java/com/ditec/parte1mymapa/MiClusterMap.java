package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

public class MiClusterMap extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    GoogleMap mapa; ClusterManager<MiClusterItem> miClusterItemClusterManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cluster_map);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-18.013766, -70.255331), 15));
        mapa.setOnMapClickListener(this);
    }
    @Override
    public void onMapClick(LatLng latLng) {
        if (miClusterItemClusterManager == null)
            miClusterItemClusterManager = new ClusterManager<MiClusterItem>(this, mapa);
        mapa.setOnCameraIdleListener(miClusterItemClusterManager);
        mapa.setOnMarkerClickListener(miClusterItemClusterManager);
        double lat = latLng.latitude;
        double lon = latLng.longitude;
        miClusterItemClusterManager.addItem(new MiClusterItem(lat, lon));
        miClusterItemClusterManager.cluster();
        final CustomClusterRenderer renderer = new CustomClusterRenderer(this, mapa, miClusterItemClusterManager);
        miClusterItemClusterManager.setRenderer(renderer);
    }
}