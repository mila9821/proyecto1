package com.ditec.parte1mymapa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CustomClusterRenderer  extends DefaultClusterRenderer<MiClusterItem> {
    private final Context mContext;
    private final IconGenerator mClusterIconGenerator;

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MiClusterItem> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
        mClusterIconGenerator = new IconGenerator(mContext.getApplicationContext());
    }
    @Override protected void onBeforeClusterItemRendered(MiClusterItem item,
                                                         MarkerOptions markerOptions) {
        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        markerOptions.icon(markerDescriptor).snippet("mi marker");
    }
    @Override
    protected void onBeforeClusterRendered(Cluster<MiClusterItem> cluster,
                                           MarkerOptions markerOptions) {
        mClusterIconGenerator.setBackground(
                ContextCompat.getDrawable(mContext, R.drawable.background_circle));
        mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);
        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }
    @Override
    protected boolean shouldRenderAsCluster(Cluster<MiClusterItem> cluster) {
        return cluster.getSize() > 1;
    }

    @Override
    protected int getColor(int clusterSize) {
        String color = "#80DEEA";
        switch(clusterSize) {
            case 10:
                color = "#EDEA24";
                break;
            case 20:
                color = "#4CAF50";
                break;
            case 50:
                color = "#00BCD4";
                break;
            case 100:
                color = "#00ACC1";
                break;
            case 200:
                color = "#0097A7";
                break;
            case 500:
                color = "#00838F";
                break;
            case 1000:
                color = "#006064";
                break;
        }
        return Color.parseColor(color);
    }

    @Override
    protected int getBucket(Cluster<MiClusterItem> cluster) {
        return cluster.getSize();
    }
    @Override
    protected String getClusterText(int bucket) {
        return String.valueOf(bucket);
    }
}
