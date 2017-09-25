package dmcjj.rmitpp.toiletlocator.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.File;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.geo.MyLocationListener;
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
    @BindView(R.id.editLat) EditText editLat;
    @BindView(R.id.editLng) EditText editLng;
    //private ImageView testImage;
    @BindView(R.id.checkMale) CheckBox checkMale;
    @BindView(R.id.checkFemale) CheckBox checkFemale;
    @BindView(R.id.checkDisabled) CheckBox checkDisabled;
    @BindView(R.id.checkUnisex) CheckBox checkUnisex;
    //private RecyclerView imageRecyler;
    @BindView(R.id.imageRecycler) RecyclerView imageRecycler;

    private BitmapAdapter imageAdapter;


    //private GeoCoord coord = GeoCoord.NULL;

    private Uri imageUri;
    private File mPhotoFile;

    private OnCompleteListener<Void> toiletCompleteListener = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Log.d("toiletlist", "OnComplete");
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
            editLat.setText(""+location.getLatitude());
            editLng.setText(""+location.getLongitude());


        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtoilet, menu);

        return true;
    }

    private void sendToDb() {
        //FireToilet.ClientBuilder toilet = new FireToilet.ClientBuilder();

        try{
            String name = editName.getText().toString();
            double lat = Double.parseDouble(editLat.getText().toString());
            double lng= Double.parseDouble(editLng.getText().toString());
            boolean isMale = checkMale.isChecked();
            boolean isFemale = checkFemale.isChecked();
            boolean isUnisex = checkUnisex.isChecked();
            boolean isDisabled = checkUnisex.isChecked();
            boolean isIndoor = checkUnisex.isChecked();


            List<Bitmap> images = imageAdapter.getImages();

            if(!Util.isNull(name)){
               // Database.putToilet(toilet);
                ToiletValues toilet = ToiletValues.create(name, lat, lng, isMale, isFemale, isUnisex, isDisabled, isIndoor);
                Database.putToilet(this,toilet, images).addOnCompleteListener(this, toiletCompleteListener).addOnFailureListener(this, toiletFailureListener);
            }
        }catch(Exception e){

        }
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

        imageAdapter = new BitmapAdapter();

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
