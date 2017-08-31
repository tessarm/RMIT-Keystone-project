package dmcjj.rmitpp.toiletlocator.geo;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by A on 31/08/2017.
 */

public class GeoCoord
{
    public static final GeoCoord NULL = new GeoCoord(0,0);
    private double lat;
    private double lng;

    public GeoCoord(){

    }

    public static GeoCoord fromLocation(Location l){
        return new GeoCoord(l.getLatitude(), l.getLongitude());
    }

    public GeoCoord(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public void setLng(double lng){
        this.lng = lng;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLng(){
        return lng;
    }
    public double getLat(){
        return lat;
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lng);
    }
}
