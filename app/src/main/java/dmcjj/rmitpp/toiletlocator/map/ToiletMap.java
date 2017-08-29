package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 29/08/2017.
 */

public class ToiletMap
{
    private GoogleMap googleMap;

    private ToiletMap(GoogleMap googleMap){
        this.googleMap = googleMap;
    }

    public static ToiletMap createMap(GoogleMap googleMap){
        return new ToiletMap(googleMap);
    }

    public void addMarker(MarkerOptions m){
        MarkerOptions op = new MarkerOptions();

        googleMap.addMarker(m);
    }

    public void setDefaultMarker(double lat, double lng){
        setDefaultMarker(new LatLng(lat, lng));

    }
    public void setDefaultMarker(LatLng latLng){
        MarkerOptions op = new MarkerOptions();
        op.position(latLng);
        addMarker(op);
    }

    public void setDefaultMarker(Location location){
        setDefaultMarker(location.getLatitude(), location.getLongitude());
    }
}
