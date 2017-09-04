package dmcjj.rmitpp.toiletlocator.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * Created by A on 2/09/2017.
 */

public class ImageUploadService extends IntentService
{
    public static String EXTRA_IMAGE = "extra_image";

    public ImageUploadService() {
        super("ImageUploadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        try{
            Bitmap imageBitmap = (Bitmap)intent.getExtras().get(EXTRA_IMAGE);

            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, boas);
            byte[] byteData = boas.toByteArray();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference sref = storage.getReference("images/toilets/" + UUID.randomUUID() + ".png");
            sref.putBytes(byteData);

        }catch(Exception e){

        }

    }
}
