package dmcjj.rmitpp.toiletlocator.activity;


import android.content.Intent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;


import butterknife.BindView;
import butterknife.ButterKnife;
import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.geo.GeoHelper;
import dmcjj.rmitpp.toiletlocator.geo.MyLocationListener;
import dmcjj.rmitpp.toiletlocator.map.IRestroomMap;
import dmcjj.rmitpp.toiletlocator.map.RestroomMap;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.view.CommentAdapter;
import dmcjj.rmitpp.toiletlocator.view.BitmapAdapter;
import dmcjj.rmitpp.toiletlocator.view.NetworkImageAdapter;
import dmcjj.rmitpp.toiletlocator.view.SimpleDividerItemDecoration;
import dmcjj.rmitpp.toiletlocator.view.UiHandler;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    //final vars
    private static final int PERMISSIONCHECK = 1;

    private SupportMapFragment mMapFragment;
    private IRestroomMap mRestroomMap;
    private CommentAdapter mCommentAdapter;
    private BitmapAdapter mImageAdapter;
    private NetworkImageAdapter mNetworkAdapter;

    //UI REFS
    @BindView(R.id.textTitle) TextView mTextTitle;
    @BindView(R.id.rating) RatingBar mRatingBar;
    @BindView(R.id.textDistance) TextView mTextDistance;
    @BindView(R.id.slideFrame) View mFrameLayout;
    @BindView(R.id.actionDetails) View mActionButton;
    @BindView(R.id.cardInfoPanel) View mViewLayout;
    @BindView(R.id.buttonDetails) Button mButtonDetails;
    @BindView(R.id.commentRecycler) RecyclerView mCommentRecycler;
    @BindView(R.id.imageRecycler) RecyclerView mImageRecycler;

    //Handler for Ui events from map
    private UiHandler mUiHandler = new UiHandler() {
        @Override
        public boolean onToiletClicked(DataSnapshot t) {
            if (t != null) {
                showInfoPanel(true);
                Database.putToiletView(t.getKey());
                Log.d("mapsact", "OnToiletClickec:key=" + t.getKey());
                bindToilet2View(t);
                return true;
            }
            return false;
        }
    };

    //Current location listener
    private MyLocationListener mCurrentLocationListener = new MyLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(mRestroomMap != null)
                mRestroomMap.onLocationUpdate(location);
            DataSnapshot currentToilet = mRestroomMap.getCurrentToilet();
            if(currentToilet != null) {
                Toilet t = currentToilet.getValue(Toilet.class);
                setDistance(t, location);
            }
        }
    };

    //Activity overrides
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONCHECK) {
            mMapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addToilet: {
                Intent addToiletIntent = new Intent(this, AddToiletActivity.class);
                startActivity(addToiletIntent);
            }
            break;
            case R.id.signOut:{
                FirebaseAuth.getInstance().signOut();
                finish();
            }
            break;
            case R.id.viewToilets:{
                Intent i = new Intent(this, ToiletViewActivity.class);
                startActivity(i);
            }break;
        }
        return true;



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        else
            GeoHelper.getLocationManager(this).requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, mCurrentLocationListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GeoHelper.getLocationManager(this).removeUpdates(mCurrentLocationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        mCommentAdapter = new CommentAdapter();
        mImageAdapter = new BitmapAdapter();
        mNetworkAdapter = new NetworkImageAdapter(this);

        //setup recycler
        mCommentRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCommentRecycler.setAdapter(mCommentAdapter);
        mCommentRecycler.addItemDecoration(new SimpleDividerItemDecoration(this));

        mImageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mImageRecycler.setAdapter(mNetworkAdapter);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mMapFragment.getMapAsync(this);

        mNetworkAdapter.add("https://firebasestorage.googleapis.com/v0/b/toilet-locator-pp1.appspot.com/o/images%2Fef6dbcb3-a11c-48a1-adc7-1d08e16270b6.png?alt=media&token=eacd278e-8182-4faa-a30c-e85283be77c6");
        mNetworkAdapter.add("https://firebasestorage.googleapis.com/v0/b/toilet-locator-pp1.appspot.com/o/images%2Fd46d2ad1-a2fd-421e-9dea-2256c0bb5a40.png?alt=media&token=966a6f0c-3c22-43f7-b72f-5fe50372757a");
        mNetworkAdapter.add("https://firebasestorage.googleapis.com/v0/b/toilet-locator-pp1.appspot.com/o/images%2Fc85741f9-fc37-425f-a10f-56122c5c9086.png?alt=media&token=ca55f481-1887-4324-a7ad-902dff4906f5");



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        //get best last known location
        Location lastLocation = GeoHelper.getBestLastKnownLocation(this, new LatLng(-37.818212, 144.966355));
        mRestroomMap = new RestroomMap(googleMap, lastLocation, mUiHandler);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //set google map UI Settings
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }


    public void onToggleFrame(View v){
        if(mFrameLayout.getVisibility() == View.GONE)
            mFrameLayout.setVisibility(View.VISIBLE);
        else
            mFrameLayout.setVisibility(View.GONE);

    }
    public void onToiletClick(View v){
        DataSnapshot currentToilet = mRestroomMap.getCurrentToilet();
        Toilet t = currentToilet.getValue(Toilet.class);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(t.value.getName());
        builder.setMessage(String.format("loc=%f,%f\nrating=%f\ntimestamp=%d\nowner=%s\nviews=%d",
                t.value.getLat(), t.value.getLng(), t.metadata.rating, t.metadata.timestamp, t.metadata.owner, -1));

        builder.create().show();
    }

    public void onDirections(View v){
        DataSnapshot currentToilet = mRestroomMap.getCurrentToilet();
        Toilet t = currentToilet.getValue(Toilet.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(t.value.getName());
        builder.setMessage("Begin directions to " + t.value.getName());

        builder.create().show();
    }

    private void setDistance(Toilet t, Location location){
        Location toiletLocation = GeoHelper.toLocation(t.value.getLat(), t.value.getLng());
        float dist = toiletLocation.distanceTo(location);
        mTextDistance.setText(String.format("%.0f", dist) + "m");
    }

    private void bindToilet2View(DataSnapshot toilet){
        mCommentAdapter.setSnapshot(toilet.child("comments"));
        Toilet t = toilet.getValue(Toilet.class);

        Location mMyLocation = mRestroomMap.getLastLocation();

        mTextTitle.setText(t.value.getName());
        if(mMyLocation != null)
            setDistance(t, mMyLocation);
        mRatingBar.setRating(t.metadata.rating);
    }


    private void showInfoPanel(boolean show){
        if(show){
            mActionButton.setVisibility(View.VISIBLE);
            mViewLayout.setVisibility(View.VISIBLE);
        }
        else{
            mActionButton.setVisibility(View.INVISIBLE);
            mViewLayout.setVisibility(View.INVISIBLE);
        }
    }
}
