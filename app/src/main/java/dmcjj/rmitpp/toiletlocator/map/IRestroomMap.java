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
<<<<<<< HEAD

    void focusToilet(String key, double lat, double lng);
=======
>>>>>>> 909ecb5e1d581e1bc7e27e13159c6b6c93b1115b

    void focusToilet(String key);
    void getNearestToilet();

    void focusToilet(String key);

}
