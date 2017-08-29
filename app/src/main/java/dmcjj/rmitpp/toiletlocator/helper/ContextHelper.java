package dmcjj.rmitpp.toiletlocator.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.LocationSource;

import java.io.File;

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
    public File getInternal(Context c, String filename){
        File inter = c.getFilesDir();

        File newFile = new File(inter, filename);
        Log.i("filename", newFile.getAbsolutePath());

        return newFile;
    }
}
