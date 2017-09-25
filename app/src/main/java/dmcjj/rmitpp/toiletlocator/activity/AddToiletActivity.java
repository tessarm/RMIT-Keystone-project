package dmcjj.rmitpp.toiletlocator.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.geo.MyLocationListener;
import dmcjj.rmitpp.toiletlocator.map.IRestroomMap;
import dmcjj.rmitpp.toiletlocator.model.ToiletValues;
import dmcjj.rmitpp.toiletlocator.helper.Util;
import dmcjj.rmitpp.toiletlocator.view.BitmapAdapter;

/**
 * Created by A on 31/08/2017.
 */

public class AddToiletActivity extends AppCompatActivity
{
    private static final int REQUEST_IMAGE_CAPTURE = 12;
    @BindView(R.id.editName) EditText editName;
    @BindView(R.id.editAddress) EditText editAddress;
    //private ImageView testImage;
    @BindView(R.id.checkMale) CheckBox checkMale;
    @BindView(R.id.checkFemale) CheckBox checkFemale;
    @BindView(R.id.checkDisabled) CheckBox checkDisabled;
    @BindView(R.id.checkUnisex) CheckBox checkUnisex;
    //private RecyclerView imageRecyler;
    @BindView(R.id.imageRecycler) RecyclerView imageRecycler;

    private BitmapAdapter imageAdapter;

    //private IRestroomMap mRestroomMap;

    Geocoder geocoder;
    List<Address> addresses;
    private double geoLat = 0;
    private double geoLong = 0;
    private String address = "";
    private RequestQueue mRequestQue;
    private static final String mGetUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String apiKey = "&key=AIzaSyD9wZodCTrMjtE0zuwuQ-LKfZ9Z6UFNIvA";
    private String latString = "";
    private String lngString = "";

    //private GeoCoord coord = GeoCoord.NULL;

    private Uri imageUri;
    private File mPhotoFile;

    private OnCompleteListener<Void> toiletCompleteListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Log.d("toiletlist", "OnComplete");
            finish();
        }
    };
    private OnFailureListener toiletFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d("toiletlist", e.getMessage());
        }
    };

    private LocationListener locationListener = new MyLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            geoLat = location.getLatitude();
            geoLong = location.getLongitude();


            try {
                addresses = geocoder.getFromLocation(geoLat, geoLong, 1);
                Address ad = addresses.get(0);
                String address = ad.getAddressLine(0);
                editAddress.setText(address);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            editLat.setText(""+location.getLatitude());
//            editLng.setText(""+location.getLongitude());
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtoilet, menu);

        return true;
    }

    private void sendToDb() {
        //FireToilet.ClientBuilder toilet = new FireToilet.ClientBuilder();

            String sendAddress = editAddress.getText().toString();
            Geocoder sendGeocoder = new Geocoder(this, Locale.getDefault());



            String address = editAddress.getText().toString().replace(' ', '+');
            //replace spaces with +'s so that the url we send works
            Log.d(address, address);
            String getUrl = mGetUrl+address+apiKey;
            //final url for the address to return a json file
            // url + the adress we obtained from the geolocater or user input + our api key
            Log.d("url", getUrl);
            mRequestQue.add(new JsonObjectRequest(getUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    List<Bitmap> images = imageAdapter.getImages();
                    String name = editName.getText().toString();
                    double lat = 0;
                    double lng= 0;
                    boolean isMale = checkMale.isChecked();
                    boolean isFemale = checkFemale.isChecked();
                    boolean isUnisex = checkUnisex.isChecked();
                    boolean isDisabled = checkUnisex.isChecked();
                    boolean isIndoor = checkUnisex.isChecked();




                    JSONArray jsonArray = null;
                    try {
                        jsonArray = response.getJSONArray("results");
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObj = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                            lat = jsonObj.getDouble("lat");
                            lng = jsonObj.getDouble("lng");

                            //iterate through the returned json file to find the latitude and longitude of the corresponding adress
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    if(!Util.isNull(name)){
                        // Database.putToilet(toilet);
                        ToiletValues toilet = ToiletValues.create(name, lat, lng, isMale, isFemale, isUnisex, isDisabled, isIndoor);
                        try {
                            Database.putToilet(AddToiletActivity.this,toilet, images)
                                    .addOnCompleteListener(AddToiletActivity.this, toiletCompleteListener)
                                    .addOnFailureListener(AddToiletActivity.this, toiletFailureListener);



                        } catch (FirebaseException e) {
                            e.printStackTrace();
                        }
                    }



                }
            }, null));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actDone: {
                sendToDb();
            }
            break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                Bitmap m =BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath());

                Bitmap newMap = m.createScaledBitmap(m, 512, 340, false);


                imageAdapter.add(newMap);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtoilet);
        ButterKnife.bind(this);

        mRequestQue = Volley.newRequestQueue(this);
        imageAdapter = new BitmapAdapter();
        geocoder = new Geocoder(this);

        imageRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        imageRecycler.setAdapter(imageAdapter);

        findViewById(R.id.fabCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        //Get Single location update
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //do something if no perm
        }else
            manager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, Looper.myLooper());

//            try {

//                Location myGeoLocation = mRestroomMap.getLastLocation();
//                geoLat = myGeoLocation.getLatitude();
//                geoLong = myGeoLocation.getLongitude();
//                    addresses = geocoder.getFromLocation(geoLat, geoLong, 1);
//                String address = addresses.get(0).getAddressLine(0);
//                editAddress.setText(address);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


    }

    private void dispatchTakePictureIntent() {
        File tempDir = getExternalCacheDir();

        mPhotoFile = new File(tempDir, UUID.randomUUID().toString());


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            // Continue only if the File was successfully created

            if (mPhotoFile != null) {
                Uri photoURI = Uri.fromFile(mPhotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }




    private void setPic(ImageView imageView) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        //BitmapFactory.decodeFile(filePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;



        //Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        //imageView.setImageBitmap(bitmap);
    }


}
