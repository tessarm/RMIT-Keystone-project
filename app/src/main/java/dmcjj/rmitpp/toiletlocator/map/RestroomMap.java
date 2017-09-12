package dmcjj.rmitpp.toiletlocator.map;

import android.location.Location;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmcjj.rmitpp.toiletlocator.DbRef;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.view.UiHandler;

/**
 * Created by A on 8/09/2017.
 *
 * This class draws toilet data
 *
 */

public class RestroomMap implements IRestroomMap
{
    private static final double MAX_SEARCH_RADIUS = 50;
    //class init
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private ArrayMap<String, DataSnapshot> mGeoToiletMap = new ArrayMap<>();
    private ArrayMap<String, DataSnapshot> mMarkerId2Toilet = new ArrayMap<>();
    private ArrayMap<String, Marker> mKey2Marker = new ArrayMap<>();

    //final vars
    private final GoogleMap mGoogleMap;
    private final GeoQuery mGeoQuery;

    //
    private UiHandler mUiHandler;
    private MyLocation mMyLocation;

    private double mSearchRadius = 5;
    private int mCameraZoom = 13;


    private Marker mRedMarker,mCurrentSelectedMarker;
    private DataSnapshot mCurrentToilet;



    //called when a new toilet value enters a location
    private ValueEventListener mToiletValueListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            final Toilet toilet = dataSnapshot.getValue(Toilet.class);
            final String toiletKey = dataSnapshot.getKey();

            Marker toiletMarker = null;

            if(mKey2Marker.containsKey(toiletKey)) {

                toiletMarker = mKey2Marker.get(toiletKey);
            }

            else{
                toiletMarker = MarkerFactory.createFromToilet(mGoogleMap, toilet);
            }

            mGeoToiletMap.put(toiletKey, dataSnapshot);
            mKey2Marker.put(toiletKey, toiletMarker);
            mMarkerId2Toilet.put(toiletMarker.getId(), dataSnapshot);

            Log.d("restroom", toilet.toString());
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private GoogleMap.OnMarkerClickListener mMarkerClickListsner = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerClickId = marker.getId();

            if(marker == mRedMarker)
                return false;
            //if select another marker set view to that toilet
            if(mCurrentSelectedMarker != null)
                mCurrentSelectedMarker.setVisible(true);
            mCurrentSelectedMarker = marker;
            mCurrentSelectedMarker.setVisible(false);
            mRedMarker.setPosition(marker.getPosition());
            mRedMarker.setVisible(true);
            mCurrentToilet = mMarkerId2Toilet.get(markerClickId);

            return mUiHandler.onToiletClicked(mCurrentToilet);

        }
    };

    //THE MAIN ENTRY POINT FOR TOILET DATA
    private GeoQueryEventListener mGeoEvent = new GeoQueryEventListener() {
        @Override
        public void onKeyEntered(String toiletKey, GeoLocation location) {
            mGeoToiletMap.put(toiletKey, null);
            Log.d("geofire", String.format("OnKeyEntered:key=%s|lat=%f|lng=%f", toiletKey, location.latitude, location.longitude));
            mDatabase.getReference(DbRef.DBREF_TOILETS_DATA + "/"+toiletKey).addValueEventListener(mToiletValueListener);
        }

        @Override
        public void onKeyExited(String toiletKey) {
            mGeoToiletMap.remove(toiletKey);
            mDatabase.getReference(DbRef.DBREF_TOILETS_DATA + "/"+toiletKey).removeEventListener(mToiletValueListener);
            Log.d("geofire", "OnKeyExited:key="+toiletKey);
        }


        @Override
        public void onGeoQueryReady() {
            Log.d("geofire", String.format("OnGeoQueryReady:size=%d|queryRadius=%f|queryLat=%f|quertLng=%f", mGeoToiletMap.size(), mSearchRadius, mGeoQuery.getCenter().latitude, mGeoQuery.getCenter().longitude));
            if(mGeoToiletMap.size() == 0 && mSearchRadius < MAX_SEARCH_RADIUS){
                mSearchRadius *= 2;
                mGeoQuery.setRadius(mSearchRadius);
            }
        }
        @Override public void onKeyMoved(String key, GeoLocation location) {}
        @Override public void onGeoQueryError(DatabaseError error) {Log.d("geofire", error.getMessage());}
    };

    private RestroomMap(GoogleMap googleMap, Location startLocation, UiHandler uiHandler){
        this.mGoogleMap = googleMap;
        this.mMyLocation = MyLocation.create().update(startLocation);
        this.mUiHandler = uiHandler;

        //GeoFire geoFire = new GeoFire(mDatabase.getReference(DbRef.DBREF_GEOFIRE_TOILETS));
        mGeoQuery = DbRef.GEOFIRE_TOILETS.queryAtLocation(new GeoLocation(startLocation.getLatitude(), startLocation.getLongitude()),mSearchRadius);
        mGeoQuery.addGeoQueryEventListener(mGeoEvent);

        MarkerOptions selectedOps = new MarkerOptions();
        selectedOps.position(new LatLng(0,0));
        selectedOps.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        selectedOps.visible(false);

        mRedMarker = googleMap.addMarker(selectedOps);

        //Animate google map to init position
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(startLocation.getLatitude(), startLocation.getLongitude()), mCameraZoom));

        googleMap.setOnMarkerClickListener(mMarkerClickListsner);

    }

    private void addMarker(String msg, GeoLocation geoLocation){
        MarkerOptions ops = new MarkerOptions();
        ops.position(new LatLng(geoLocation.latitude, geoLocation.longitude));
        ops.title(msg);

        mGoogleMap.addMarker(ops);
    }



    public static RestroomMap create(GoogleMap googleMap, Location startLocation, UiHandler uiHandler)
    {
        return new RestroomMap(googleMap, startLocation, uiHandler);
    }

    //INTERFACE HERE

    @Override
    public void onLocationUpdate(Location location) {
        mMyLocation.update(location);
        mGeoQuery.setCenter(mMyLocation.getGeoLocation());
    }

    @Override
    public Location getLastLocation() {
        return mMyLocation.getLocation();
    }

    @Override
    public DataSnapshot getCurrentToilet() {
        return mCurrentToilet;
    }

}