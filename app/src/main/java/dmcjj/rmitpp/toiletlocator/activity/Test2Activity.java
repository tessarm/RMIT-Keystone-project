package dmcjj.rmitpp.toiletlocator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dmcjj.rmitpp.toiletlocator.Database;
import dmcjj.rmitpp.toiletlocator.view.FirebaseRealtimeList;

/**
 * Created by A on 1/09/2017.
 */

public class Test2Activity extends AppCompatActivity
{
    private FirebaseRealtimeList.EventListener<String> eventListener = new FirebaseRealtimeList.EventListener<String>() {
        @Override
        public void onAdd(String data) {
            Log.i("server_errors", data);
        }

        @Override
        public void onModified(String data) {

        }

        @Override
        public void onRemoved(String data) {

        }
    };
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        DatabaseReference ref = Database.getUserRef().child("geofire/toilets");

        GeoFire geoFire = new GeoFire(ref);

        //geoFire.queryAtLocation(new GeoLocation(0,0), 1).;


        FirebaseRealtimeList<String> toiletFirebaseList = FirebaseRealtimeList.newList("test/strings", String.class);

        toiletFirebaseList.connect(eventListener);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference sref = storage.getReference("images/toilets/uuid9384823");












        geoFire.setLocation("toilet_id", new GeoLocation(12.38, 38.938));








    }
}
