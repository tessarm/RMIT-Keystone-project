package dmcjj.rmitpp.toiletlocator.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.service.ImageUploadService;

public class UserAreaActivity extends AppCompatActivity
{
    private static final int REQUEST_IMAGE_CAPTURE = 8374;

    private Button mButtonMap;

    private ChildEventListener childListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            //Toast.makeText(UserAreaActivity.this, (String)dataSnapshot.child("name").getValue(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);


        mButtonMap = (Button)findViewById(R.id.buttonMap);
        mButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapsIntent = new Intent(UserAreaActivity.this, MapsActivity.class);
                UserAreaActivity.this.startActivity(mapsIntent);
            }
        });

        //TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvTest);
        //EditText etUsername = (EditText) findViewById(R.id.etUsername);
        //EditText etEmail = (EditText) findViewById(R.id.etEmail);
        ///etEmail.setText("admin@admin.com");
        //etUsername.setText("admin");
        /*
        tvWelcomeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(UserAreaActivity.this, MapsActivity.class);
                UserAreaActivity.this.startActivity(mapsIntent);
            }
        });
        */

        //TODO TEST CODE
        //Database.putComment("-KsrJx1tZzT4jz6HKT06", "This toilet is the best, maccas for the win");
        FirebaseDatabase.getInstance().getReference().child("toilets").
                addChildEventListener(childListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_area, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case R.id.emailDevelopers:{

            }break;
            case R.id.signOut:{
                FirebaseAuth.getInstance().signOut();
                finish();
            }
            case R.id.viewToilets:{
                Intent i = new Intent(this, ToiletViewActivity.class);
                startActivity(i);
            }break;
            case R.id.imageUpload:{
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }break;
            case R.id.test:{
                Intent i = new Intent(this, Test2Activity.class);
                startActivity(i);
            }break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent imageUpload = new Intent(this, ImageUploadService.class);
            imageUpload.putExtra(ImageUploadService.EXTRA_IMAGE, imageBitmap);
            startService(imageUpload);




            //mImageView.setImageBitmap(imageBitmap);
        }
    }
}
