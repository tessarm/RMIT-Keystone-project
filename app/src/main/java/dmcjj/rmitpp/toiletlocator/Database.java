package dmcjj.rmitpp.toiletlocator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dmcjj.rmitpp.toiletlocator.model.Review;
import dmcjj.rmitpp.toiletlocator.model.FirebaseMetaData;
import dmcjj.rmitpp.toiletlocator.model.ToiletValues;
import dmcjj.rmitpp.toiletlocator.server_model.LoginMeta;
import dmcjj.rmitpp.toiletlocator.service.ImageUploadService;
import dmcjj.rmitpp.toiletlocator.service.ImageUploadTask;

/**
 * Created by A on 31/08/2017.
 */

public class Database
{

    public static Task<Void> putToilet(final Context c, final ToiletValues t, final List<Bitmap> bitmaps) throws FirebaseException {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null)
            throw new FirebaseException("No Current Auth User");
        //push toilet object
        final FirebaseMetaData metaData = new FirebaseMetaData().setOwner().setTimestamp();

        final DatabaseReference tKey = DbRef.DATABASE.getReference(DbRef.DBREF_TOILETS_DATA).push();


        Map<String, Object> tData = new HashMap<>();
        tData.put(DbRef.VALUE, t);
        tData.put(DbRef.META_DATA, metaData);


        Task<Void> task = tKey.setValue(tData);
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ImageUploadTask uploadTask = new ImageUploadTask(tKey.getKey(), bitmaps);

                    Thread t = new Thread(uploadTask);
                    t.start();


                    /*
                    Context appContext = c.getApplicationContext();
                    for(Bitmap b : bitmaps){
                        Intent imageUpload = new Intent(appContext, ImageUploadService.class);
                        //imageUpload.putExtra(ImageUploadService.EXTRA_IMAGE, b);
                        imageUpload.putExtra(ImageUploadService.EXTRA_KEY, tKey.getKey());
                        appContext.startService(imageUpload);

                    }
                    */
                }

                else{
                    Exception e = task.getException();
                    Log.d("firebase", e.getMessage());
                }
            }
        });
        return task;
        //set geofire
    }

    public static void putReview(String toiletKey, int rating, String comment){
        //final FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
            return;
        //push toilet object
        Review review = new Review(user.getUid(), comment, rating);


        final DatabaseReference cRef = DbRef.DATABASE.getReference(DbRef.getRefToiletComments(toiletKey));
        cRef.setValue(review);
    }

    public static Task<Void> putLogin(LoginMeta loginMeta)
    {
        return DbRef.DATABASE.getReference(DbRef.DBREF_LOGIN).push().setValue(loginMeta);
    }

    public static Transaction.Result putToiletView(String toiletKey)
    {
        DatabaseReference tRef = DbRef.DATABASE.getReference(DbRef.DBREF_TOILETS_DATA).
                child(toiletKey).child(DbRef.STATS_VIEW);

        tRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue() == null)
                {
                    mutableData.setValue(1);
                }
                else{
                    int count = mutableData.getValue(int.class);
                    count++;
                    mutableData.setValue(count);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                if(databaseError != null)
                    Log.d("firebase", databaseError.getMessage());
            }
        });

        return Transaction.abort();
        //return FirebaseDatabase.getInstance().getReference(DbRef.SERVICE_QUEUE).push().setValue(object);
    }

    public static void putToiletUrl(String mToiletKey, String url)
    {
        DatabaseReference ref=  DbRef.DATABASE.getReference(DbRef.getToiletImages(mToiletKey));
        ref.push().setValue(url);
    }

    public static void putToiletUrlThumb(String mToiletKey, String url) {
        DatabaseReference ref=  DbRef.DATABASE.getReference(DbRef.getToiletImagesThumb(mToiletKey));
        ref.push().setValue(url);
    }
}
