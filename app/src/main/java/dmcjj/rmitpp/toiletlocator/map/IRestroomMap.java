package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;

import com.google.firebase.database.DataSnapshot;

import dmcjj.rmitpp.toiletlocator.interfaces.ILocation;

/**
 * Created by A on 10/09/2017.
 */

public interface IRestroomMap extends ILocation
{
    Location getLastLocation();
    DataSnapshot getCurrentToilet();
    void focusToilet(String key, double lat, double lng);
}
