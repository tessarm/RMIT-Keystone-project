package dmcjj.rmitpp.toiletlocator.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.LocationSource;

/**
 * Created by A on 28/08/2017.
 */

public class ContextHelper
{
    public static void getNewLocation(Context c, LocationListener locationChangedListener) {
        LocationManager manager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationChangedListener, Looper.myLooper());

    }
}
