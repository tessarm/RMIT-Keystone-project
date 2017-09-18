package dmcjj.rmitpp.toiletlocator.service;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.UUID;

import dmcjj.rmitpp.toiletlocator.Database;

/**
 * Created by A on 15/09/2017.
 */

public class ImageUploadTask implements Runnable
{
    private String mToiletKey;
    private List<Bitmap> mBitmaps;


    //private OnSuccessListener<UploadTask.TaskSnapshot> mSuccess;
    //private OnFailureListener mFailure;

    private OnSuccessListener<UploadTask.TaskSnapshot> mLocalFullSuccess = new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Log.d("imageupload", taskSnapshot.getDownloadUrl().toString());
            Log.d("imageupload", Thread.currentThread().getName());

            Database.putToiletUrl(mToiletKey, taskSnapshot.getDownloadUrl().toString());
        }
    };

    private OnSuccessListener<UploadTask.TaskSnapshot> mLocalThumbSuccess = new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Log.d("imageupload", taskSnapshot.getDownloadUrl().toString());
            Log.d("imageupload", Thread.currentThread().getName());

            Database.putToiletUrlThumb(mToiletKey, taskSnapshot.getDownloadUrl().toString());
        }
    };

    public ImageUploadTask(String toiletKey, List<Bitmap> bitmaps){
        this.mToiletKey = toiletKey;
        this.mBitmaps = bitmaps;

    }





    @Override
    public void run()
    {
        for(Bitmap bitmap: mBitmaps)
        {
            try
            {

                //FULL SIZE IMAGE
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, boas);
                byte[] byteData = boas.toByteArray();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference sref = storage.getReference("images/" + UUID.randomUUID() + ".png");


                UploadTask task = sref.putBytes(byteData);
                task.addOnSuccessListener(mLocalFullSuccess);

                //THUMB NAIL
                Bitmap thumb =  Bitmap.createScaledBitmap(bitmap, 150, 100, false);


                ByteArrayOutputStream boasThumb = new ByteArrayOutputStream();
                thumb.compress(Bitmap.CompressFormat.PNG, 100, boasThumb);
                byte[] byteDataThumb = boasThumb.toByteArray();

                StorageReference srefThumb = storage.getReference("images/" + UUID.randomUUID() + ".png");


                UploadTask taskThumb = srefThumb.putBytes(byteDataThumb);
                taskThumb.addOnSuccessListener(mLocalThumbSuccess);



            }catch (Exception e){

            }
        }
    }
}
