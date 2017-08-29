package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by A on 29/08/2017.
 */

public class MyLocation
{
    private LatLng myLatLng;
    private Location myLocation;
    private Marker myMarker;

    private MyLocation(LatLng latLng, Location location, Marker marker){
        this.myLatLng = latLng;
        this.myLocation = location;
        this.myMarker = marker;
    }

    public static MyLocation create(GoogleMap googleMap, Location startLocation){
        LatLng latlng = new LatLng(startLocation.getLatitude(), startLocation.getLongitude());
        Marker m = googleMap.addMarker(new MarkerOptions().position(latlng));
        m.setVisible(false);
        return new MyLocation(latlng, startLocation, m);

    }

    public void set(LatLng latLng){
        myMarker.setPosition(latLng);
        //set coord
        myLocation.setLatitude(latLng.latitude);
        myLocation.setLongitude(latLng.longitude);
        this.myLatLng = latLng;
    }

    public void set(Location location){
        LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        this.myLatLng = newLatLng;
        this.myLocation = location;
        myMarker.setPosition(newLatLng);
    }


    public void setVisible(boolean b) {
        myMarker.setVisible(b);
    }
}

