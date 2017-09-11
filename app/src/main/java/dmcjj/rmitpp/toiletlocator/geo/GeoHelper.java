package dmcjj.rmitpp.toiletlocator.geo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by A on 5/09/2017.
 */

public class GeoHelper {

    public static Location toLocation(double lat, double lng){
        Location toiletLocation = new Location(LocationManager.GPS_PROVIDER);
        toiletLocation.setLatitude(lat);
        toiletLocation.setLongitude(lng);


        return toiletLocation;
    }

    public static LatLng toLatLng(GeoReadable geoReadable) {

        return new LatLng(geoReadable.getLat(), geoReadable.getLng());
    }
    public static LatLng toLatLng(Location location){
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public static LocationManager getLocationManager(Context c){
        LocationManager locationManager = (LocationManager)
                c.getSystemService(Context.LOCATION_SERVICE);
        return locationManager;
    }

    public static Location getBestLastKnownLocation(Context c, LatLng latLngDef) {
        Location def = new Location(LocationManager.GPS_PROVIDER);
        def.setLatitude(latLngDef.latitude);
        def.setLongitude(latLngDef.longitude);

        LocationManager locationManager = getLocationManager(c);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return def;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if(lastKnownLocation == null)
            return def;
        return lastKnownLocation;
    }
}
