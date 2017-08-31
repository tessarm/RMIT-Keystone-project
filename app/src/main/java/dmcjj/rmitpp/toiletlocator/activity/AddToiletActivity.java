package dmcjj.rmitpp.toiletlocator.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.database.Database;
import dmcjj.rmitpp.toiletlocator.geo.GeoCoord;
import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 31/08/2017.
 */

public class AddToiletActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editLat;
    private EditText editLng;

    private GeoCoord coord = GeoCoord.NULL;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            editLat.setText(""+location.getLatitude());
            editLng.setText(""+location.getLongitude());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtoilet, menu);

        return true;
    }

    private void sendToDb() {
        String name = editName.getText().toString();
        double lat = Double.parseDouble(editLat.getText().toString());
        double lng = Double.parseDouble(editLng.getText().toString());

        Toilet t = Toilet.create(name, lat, lng);
        Database.putToilet(t);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actDone: {
                sendToDb();
                finish();
            }
            break;
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoilet);

        editName = (EditText) findViewById(R.id.editName);
        editLat = (EditText) findViewById(R.id.editLat);
        editLng = (EditText) findViewById(R.id.editLng);

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //do something if no perm
        }else
            manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, Looper.myLooper());

    }
}
