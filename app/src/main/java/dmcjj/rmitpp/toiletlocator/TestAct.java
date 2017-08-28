package dmcjj.rmitpp.toiletlocator;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.LocationSource;

import dmcjj.rmitpp.toiletlocator.web.OnToiletListener;
import dmcjj.rmitpp.toiletlocator.web.ToiletResponse;
import dmcjj.rmitpp.toiletlocator.web.ToiletTask;

/**
 * Created by A on 21/08/2017.
 */

public class TestAct extends AppCompatActivity
{
    private OnToiletListener toiletListener = new OnToiletListener() {
        @Override
        public void onToiletResponse(ToiletResponse toiletResponse) {

        }
    };


    private LocationSource.OnLocationChangedListener onLocationChangedListener = new LocationSource.OnLocationChangedListener() {
        @Override
        public void onLocationChanged(Location location) {

        }
    };






}
