package com.ditec.parte1mymapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MiMapa extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {
    GoogleMap mapa;
    LatLng ubicacion;
    Marker Mymarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_mapa);
// Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        ubicacion = new LatLng(-18.013766, -70.255331);
        mapa.setMaxZoomPreference(18);
        mapa.setMinZoomPreference(10);
        // mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
       /* mapa.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.miestilo));*/

        mapa.addMarker(new MarkerOptions().position(ubicacion).title("Mi Marcador"));
        //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(ubicacion) //Centramos el mapa en alguna latitud longitud
                .zoom(18) //Establecemos el zoom en 18
                .bearing(180) //Establecemos la orientación con el noreste arriba
                .tilt(90) //Bajamos el punto de vista de la cámara 90 grados
                .build();
        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);
        mapa.moveCamera(camUpd3);
        mapa.setOnMapClickListener(this);
        mapa.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                marker.remove();
            }
        });
        mapa.setOnCameraMoveStartedListener(this);
        mapa.setOnCameraMoveListener(this);
        mapa.setOnCameraIdleListener(this);

    }

    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
    }

    /*    public void addMarker(View view) {
            Log.i("mipuntocentro", mapa.getCameraPosition().target.toString());
            Log.i("mipuntocentro", mapa.getCameraPosition().target.latitude+"");
            Log.i("mipuntocentro", mapa.getCameraPosition().target.longitude + "");
            mapa.addMarker(new MarkerOptions().position(
                    mapa.getCameraPosition().target));
        }
        @Override public void onMapClick(LatLng puntoPulsado) {
            Log.i("mipunto",puntoPulsado.toString());
            Log.i("mipunto",puntoPulsado.latitude+"");
            Log.i("mipunto",puntoPulsado.longitude + "");
            mapa.addMarker(new MarkerOptions().position(puntoPulsado)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }*/
    public void addMarker(View view) {
        mapa.addMarker(new MarkerOptions()
                .position(mapa.getCameraPosition().target)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
    }

    @Override public void onMapClick(LatLng puntoPulsado) {
        Mymarker= mapa.addMarker(new MarkerOptions()
                .position(puntoPulsado)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.markermap))
                .title("Marker onMapClick")
                .snippet("Este marker es producto del evento de pulsar en el mapa"));
    }
    public void eliminar(View view) {
        if(Mymarker!=null)
            Mymarker.remove();
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Log.i("eventocamara","El usuario hizo un gesto en el mapa");
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Log.i("eventocamara","El usuario tocó algo en el mapa");
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Log.i("eventocamara","La aplicación movió la cámara");
        }
    }
    @Override
    public void onCameraMove() {
        Log.i("eventocamara","La camara se esta moviendo.");
    }
    @Override
    public void onCameraIdle() {
        Log.i("eventocamara","La cámara ha dejado de moverse.");
    }
}