package dmcjj.rmitpp.toiletlocator;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng melbourne = new LatLng(-37.814, 144.9632);
        mMap.addMarker(new MarkerOptions().position(melbourne).title("Marker in Melbourne"));

        LatLng toilet1 = new LatLng(-37.80312731, 144.9497264);
        mMap.addMarker(new MarkerOptions().position(toilet1).title("Queensberry st"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourne));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        // Add a marker in Sydney and move the camera
        //LatLng melbourne = new LatLng(-37.814, 144.9632);
        //mMap.addMarker(new MarkerOptions().position(melbourne).title("Marker in Melbourne"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(melbourne));

//        LatLng locationhere new LatLng(location.getLatitude(), location.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(locationhere).title("Your current location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationhere));
    }
}
