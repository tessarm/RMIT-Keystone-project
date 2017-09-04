package dmcjj.rmitpp.toiletlocator;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmcjj.rmitpp.toiletlocator.database.Database;
import dmcjj.rmitpp.toiletlocator.model.Comment;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletApp extends Application
{
    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if(user != null){
                Log.i("fireuser", user.getEmail());
                Database.putComment("-KsyKtugBbmJJIT18pcm", new Comment(user.getUid(), "this is a user comment that goes here"));
                //TODO
            }



        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);


    }
}
