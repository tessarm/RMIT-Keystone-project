package dmcjj.rmitpp.toiletlocator.database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;

import dmcjj.rmitpp.toiletlocator.model.Comment;
import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 31/08/2017.
 */

public class Database
{
    public static final String TOILET_URL = "toilets/data";
    public static final String COMMENTS_URL = "toilets/comments";
    public static final String GEOFIRE = "geofire";

    public static DatabaseReference getUserRef(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }

    public static void putToilet(final Toilet t)
    {
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        //push toilet object

        final DatabaseReference tRef = db.getReference().child(TOILET_URL).push();
        tRef.setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    GeoFire geoFire = new GeoFire(db.getReference().child(GEOFIRE));
                    geoFire.setLocation(tRef.getKey(), new GeoLocation(t.getLocation().getLat(), t.getLocation().getLng()));
                }
                else{
                    Exception e = task.getException();
                    Log.d("firebase", e.getMessage());
                }
            }
        });
        //set geofire



    }

    public static void putComment(String toiletRef, Comment comment){
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        //push toilet object


        final DatabaseReference cRef = db.getReference().child(COMMENTS_URL).child(toiletRef).push();
        cRef.setValue(comment);

        


    }

    public static void makeAdmin(String email){

    }
}
