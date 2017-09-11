package dmcjj.rmitpp.toiletlocator.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 8/09/2017.
 */

public class MarkerFactory
{
    public static Marker createFromToilet(GoogleMap googleMap, Toilet t){
        MarkerOptions ops = new MarkerOptions();
        ops.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        ops.position(new LatLng(t.value.getLat(), t.value.getLng()));
        ops.title(t.value.getName());

        Marker marker = googleMap.addMarker(ops);

        return marker;

    }
}
