package dmcjj.rmitpp.toiletlocator;


import android.location.LocationListener;
import android.os.AsyncTask;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import dmcjj.rmitpp.toiletlocator.database.TestClass;
import dmcjj.rmitpp.toiletlocator.map.ToiletMap;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.web.OnToiletListener;
import dmcjj.rmitpp.toiletlocator.web.ToiletApi;
import dmcjj.rmitpp.toiletlocator.web.ToiletResponse;
import java.util.List;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static LatLng DEFAULT_LOCATION = new LatLng(-37.817798, 144.968714);

    private static int PERMISSIONCHECK = 1;

    //private GoogleMap mMap;
    private ToiletApi toiletApi;

    private ToiletMap toiletMap;



    private boolean mapInit = false;
    private SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);

    //Current location listener
    private LocationListener currentLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            toiletMap.update(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    private OnToiletListener toiletListener = new OnToiletListener() {
        @Override
        public void onToiletResponse(int requestCode, ToiletResponse toiletResponse) {

            Toilet[] data = toiletResponse.getToiletData();
            for(int i=0; i < data.length; i++){
                Toilet t = data[i];
                toiletMap.setDefaultMarker(t.getLocation());
            }

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == PERMISSIONCHECK){
            mapFragment.getMapAsync(this);
        }
        Log.i("RequestPerm", "Requested");

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //get map frag reference
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONCHECK);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONCHECK);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }




        toiletApi = new ToiletApi(this);
        toiletApi.requestToiletData(1, toiletListener);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        mapFragment.getMapAsync(this);


        Test();




    }

    public void Test(){

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                TestClass.New();
                return null;
            }
        };
        task.execute();



    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        //mMap = googleMap;
        toiletMap = ToiletMap.createMap(googleMap);


        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            Log.d("PERM", "Permission denied");
            return;

        }

        Log.d("qwer", "qwe");

        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {

            LatLng melbourne = new LatLng(locations.getLatitude(), locations.getLongitude());

            toiletMap.onMapReady(melbourne);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, currentLocationListener);
        }
    }
}
