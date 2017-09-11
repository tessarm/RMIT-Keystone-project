package dmcjj.rmitpp.toiletlocator.model;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

/**
 * Created by A on 6/09/2017.
 */

public class GeoFireToilet
{
    public String key;
    public GeoLocation location;

    public GeoFireToilet(String key, GeoLocation location){
        this.key = key;
        this.location = location;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return key.equals(obj);
    }
}
