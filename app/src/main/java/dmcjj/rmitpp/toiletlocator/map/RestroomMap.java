package dmcjj.rmitpp.toiletlocator.map;

import android.content.Context;
import android.location.Location;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import android.widget.Toast;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmcjj.rmitpp.toiletlocator.DbRef;
import dmcjj.rmitpp.toiletlocator.activity.MapsActivity;
import dmcjj.rmitpp.toiletlocator.geo.GeoHelper;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.view.UiHandler;

/**
 * Created by A on 8/09/2017.
 *
 * This class draws toilet data
 *
 */

public class RestroomMap implements IRestroomMap, GoogleMap.OnMarkerClickListener
{
    private static final double MAX_SEARCH_RADIUS = 50;
    //class init
    private ArrayMap<String, DataSnapshot> mGeoToiletMap = new ArrayMap<>();
    private ArrayMap<String, DataSnapshot> mMarkerId2Toilet = new ArrayMap<>();
    private ArrayMap<String, Marker> mKey2Marker = new ArrayMap<>();

    //final vars
    private final GoogleMap mGoogleMap;
    private final GeoQuery mGeoQuery;

    //
    private UiHandler mUiHandler;
    private MyLocation mMyLocation;

    private double mSearchRadius = 50;
    private int mCameraZoom = 13;

    private String mPendingKey = null;
    private Marker mRedMarker,mCurrentSelectedMarker;
    private DataSnapshot mCurrentToilet;
    private boolean mAnimateLocation = true;

    private Context mContext;

    //called when a new toilet value enters a location
    private ValueEventListener mToiletValueListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot toiletSnap) {
            final Toilet toilet = toiletSnap.getValue(Toilet.class);
            final String toiletKey = toiletSnap.getKey();

            Marker toiletMarker = null;

            if (mKey2Marker.containsKey(toiletKey))
                toiletMarker = mKey2Marker.get(toiletKey);
            else
                toiletMarker = MarkerFactory.createFromToilet(mGoogleMap, toilet);


            mGeoToiletMap.put(toiletKey, toiletSnap);
            mKey2Marker.put(toiletKey, toiletMarker);
            mMarkerId2Toilet.put(toiletMarker.getId(), toiletSnap);
            if( mPendingKey != null){
                focusToilet(mPendingKey);
                mPendingKey = null;
            }
            Log.d("restroom", toilet.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //THE MAIN ENTRY POINT FOR TOILET DATA
    private GeoQueryEventListener mGeoEvent = new GeoQueryEventListener() {
        @Override
        public void onKeyEntered(String toiletKey, GeoLocation location) {
            mGeoToiletMap.put(toiletKey, null);
            Log.d("geofire", String.format("OnKeyEntered:key=%s|lat=%f|lng=%f", toiletKey, location.latitude, location.longitude));
            DbRef.DATABASE.getReference(DbRef.DBREF_TOILETS_DATA + "/" + toiletKey).addValueEventListener(mToiletValueListener);
        }

        @Override
        public void onKeyExited(String toiletKey) {
            mGeoToiletMap.remove(toiletKey);
            DbRef.DATABASE.getReference(DbRef.DBREF_TOILETS_DATA + "/" + toiletKey).removeEventListener(mToiletValueListener);
            Log.d("geofire", "OnKeyExited:key=" + toiletKey);
        }


        @Override
        public void onGeoQueryReady() {
            Log.d("geofire", String.format("OnGeoQueryReady:size=%d|queryRadius=%f|queryLat=%f|quertLng=%f", mGeoToiletMap.size(), mSearchRadius, mGeoQuery.getCenter().latitude, mGeoQuery.getCenter().longitude));
            if (mGeoToiletMap.size() == 0 && mSearchRadius < MAX_SEARCH_RADIUS) {
                mSearchRadius *= 2;
                mGeoQuery.setRadius(mSearchRadius);
            }
        }

        @Override
        public void onKeyMoved(String key, GeoLocation location) {

        }

        @Override
        public void onGeoQueryError(DatabaseError error) {
            Log.d("geofire", error.getMessage());
        }
    };

    public RestroomMap(GoogleMap googleMap, Location startLocation, UiHandler uiHandler, Context context) {
        this.mGoogleMap = googleMap;
        this.mMyLocation = MyLocation.create().update(startLocation);
        this.mUiHandler = uiHandler;
        this.mContext = context;

        //GeoFire geoFire = new GeoFire(mDatabase.getReference(DbRef.DBREF_GEOFIRE_TOILETS));
        mGeoQuery = DbRef.GEOFIRE_TOILETS.queryAtLocation(new GeoLocation(startLocation.getLatitude(), startLocation.getLongitude()), mSearchRadius);
        mGeoQuery.addGeoQueryEventListener(mGeoEvent);

        MarkerOptions selectedOps = new MarkerOptions();
        selectedOps.position(new LatLng(0, 0));
        selectedOps.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        selectedOps.visible(false);

        mRedMarker = googleMap.addMarker(selectedOps);

        //Animate google map to init position
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(startLocation.getLatitude(), startLocation.getLongitude()), mCameraZoom));

        googleMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String markerClickId = marker.getId();

        if (marker == mRedMarker)
            return false;
        //if select another marker set view to that toilet
        if (mCurrentSelectedMarker != null)
            mCurrentSelectedMarker.setVisible(true);
        mCurrentSelectedMarker = marker;
        mCurrentSelectedMarker.setVisible(false);
        mRedMarker.setPosition(marker.getPosition());
        mRedMarker.setVisible(true);
        mCurrentToilet = mMarkerId2Toilet.get(markerClickId);

        return mUiHandler.onToiletClicked(mCurrentToilet);
    }
    //INTERFACE HERE

    @Override
    public void onLocationUpdate(Location location) {
        mMyLocation.update(location);
        mGeoQuery.setCenter(mMyLocation.getGeoLocation());
        //animate location update

        if (mAnimateLocation) {
            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition pos = CameraPosition.builder().zoom(mCameraZoom).bearing(location.getBearing())
                    .target(latlng).build();

            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
        }
    }

    @Override
    public Location getLastLocation() {
        return mMyLocation.getLocation();
    }

    @Override
    public DataSnapshot getCurrentToilet() {
        return mCurrentToilet;
    }

    @Override
    public void focusToilet(String key) {

        if (mGeoToiletMap.containsKey(key)) {
            DataSnapshot toiletData = mGeoToiletMap.get(key);
            Toilet toilet = toiletData.getValue(Toilet.class);
            LatLng latlng = new LatLng(toilet.value.getLat(), toilet.value.getLng());

            CameraPosition pos = CameraPosition.builder().zoom(mCameraZoom)
                    .target(latlng).build();

            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));

            Marker mark = mKey2Marker.get(key);
            onMarkerClick(mark);
        }else{
            DbRef.DATABASE.getReference(DbRef.DBREF_TOILETS_DATA + "/"+key).addValueEventListener(mToiletValueListener);
            mPendingKey = key;
        }
        mAnimateLocation = false;
    }

    @Override
    public void getNearestToilet() {
   // function to find nearest toilet
        int arrayLoop = 0;
        float closestDistance = 5000;
        DataSnapshot closestToiletKey = null;
        Location finalLocation = null;




        int arrayCounter = mGeoToiletMap.size();
        //check the size of the tolet map array to see how many toilets there are
        if (arrayCounter >= 1) {
            for (int i = 0; i < mGeoToiletMap.size(); i++) {
        // loops through until all entries in the arraymap are checked
                String toiletName = mGeoToiletMap.keyAt(arrayLoop);
                DataSnapshot toiletKey = mGeoToiletMap.valueAt(arrayLoop);
                arrayLoop++;
                Toilet t = toiletKey.getValue(Toilet.class);
                Location toiletLocation = GeoHelper.toLocation(t.value.getLat(), t.value.getLng());
                float dist = toiletLocation.distanceTo(mMyLocation.getLocation());
                if (dist < closestDistance) {
                    // if the current toilet is closest to the previous or default max range, set the closest toilet values to match
                    closestDistance = dist;
                    closestToiletKey = toiletKey;
                    finalLocation = toiletLocation;
                }


            }


            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(finalLocation.getLatitude(), finalLocation.getLongitude()), mCameraZoom));
            mUiHandler.onToiletClicked(closestToiletKey);

            // moves the camera to the closest toilet and opens the toilet info

        }
        else{
            CharSequence text = "No Toilets nearby";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(mContext, text, duration);
            toast.show();

         //Message shown if there are no toilets in the array map, therefore no toilets nearby

        }


    }
}
