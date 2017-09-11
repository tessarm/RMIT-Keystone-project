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
import com.google.firebase.database.FirebaseDatabase;

import dmcjj.rmitpp.toiletlocator.database.Database;
import dmcjj.rmitpp.toiletlocator.database.DbRef;
import dmcjj.rmitpp.toiletlocator.geo.GeoHelper;
import dmcjj.rmitpp.toiletlocator.geo.MyLocationListener;
import dmcjj.rmitpp.toiletlocator.model.Review;
import dmcjj.rmitpp.toiletlocator.server_model.LoginMeta;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletApp extends Application {
    private MyLocationListener locationListener = new MyLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            DatabaseReference loginRef = FirebaseDatabase.getInstance().getReference(DbRef.LOGIN);

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

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);


    }
}
