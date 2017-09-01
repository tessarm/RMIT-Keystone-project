package dmcjj.rmitpp.toiletlocator.model;

import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.IgnoreExtraProperties;

import dmcjj.rmitpp.toiletlocator.geo.GeoCoord;
import dmcjj.rmitpp.toiletlocator.util.StringUtil;

/**
 * Created by A on 10/08/2017.
 */

public class Toilet
{
    public static final Toilet NULL = new Toilet();
    private static String OUTDOOR = "Outdoor";
    private static String INDOOR = "Indoor";

    private String name;
    //private double lat;
    //private double lon;
    private GeoCoord location = GeoCoord.NULL;
    //private LatLng location = new LatLng(0,0);
    private boolean disabled;
    private boolean male;
    private boolean female;
    private String indoorOutdoor;

    private Toilet(){
        name = StringUtil.NULL;
    }


    Toilet(String name, double lat, double lon, boolean disabled, boolean male, boolean female){
        this.name = name;
        this.location = new GeoCoord(lat, lon);
        //this.lat = lat;
        //this.lon = lon;
        this.disabled = disabled;
        this.male = male;
        this.female = female;

    }


    public String getName() {
        return name;
    }

    public GeoCoord getLocation() {
        return location;
    }

    public static Toilet create(String name, double lat, double lng) {
        return new Toilet(name, lat, lng, false, true, true);
    }
}
