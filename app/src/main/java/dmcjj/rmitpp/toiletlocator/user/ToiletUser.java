package dmcjj.rmitpp.toiletlocator.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by A on 31/08/2017.
 */

public class ToiletUser
{
    private static FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            Log.i("fireuser_static", user.getEmail());
        }
    };

    static{
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }




}
