package com.ditec.parte1mymapa;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MiClusterItem  implements ClusterItem {
    LatLng mposicion;
    public MiClusterItem(double lat, double lon){
        mposicion=new LatLng(lat,lon);
    }
    @Override
    public LatLng getPosition() {
        return mposicion;
    }
    @Override
    public String getTitle() {
        return null;
    }
    @Override
    public String getSnippet() {
        return null;
    }
}
