package dmcjj.rmitpp.toiletlocator;

import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import dmcjj.rmitpp.toiletlocator.geo.GeoHelper;
import dmcjj.rmitpp.toiletlocator.geo.MyLocationListener;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletApp extends Application {
    private MyLocationListener locationListener = new MyLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            DatabaseReference loginRef = DbRef.DATABASE.getReference(DbRef.DBREF_LOGIN);

            //loginRef.setValue(new LoginMeta())
        }
    };

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                Log.i("fireuser", user.getEmail());
                // Database.putReview("-Kt_0TxUQsBWRgScQ-id", 4, "this is a user comment that goes here");
                if (ActivityCompat.checkSelfPermission(ToiletApp.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ToiletApp.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //return;
                }
                else
                    GeoHelper.getLocationManager(ToiletApp.this).requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, Looper.myLooper());
                //TODO
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

//        Twitter.initialize(this);
        // above is code from the firebase/twitter documentation - need help

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);


    }
}
