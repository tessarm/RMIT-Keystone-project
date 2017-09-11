package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
    private final GeoLocation myGeoLocation;

    private boolean hasLocation;

    private MyLocation(){
        hasLocation = false;
        this.myGeoLocation = new GeoLocation(0,0);
    }

    public static MyLocation create(){
        return new MyLocation();
    }

    private void set(Location location){
        this.myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        this.myLocation = location;
        this.myGeoLocation.latitude = location.getLatitude();
        this.myGeoLocation.longitude = location.getLongitude();

    }

    public MyLocation update(Location myLocation){
        set(myLocation);
        hasLocation = true;
        return this;
    }

    public GeoLocation getGeoLocation() {
        return myGeoLocation;
    }
    public Location getLocation(){
        return myLocation;
    }
    public LatLng getLatLng(){
        return myLatLng;
    }


}

