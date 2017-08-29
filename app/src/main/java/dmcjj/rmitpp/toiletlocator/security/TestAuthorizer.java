package dmcjj.rmitpp.toiletlocator.security;

import android.content.Context;
import android.util.Log;

/**
 * Created by derrickphung on 10/8/17.
 */

public class TestAuthorizer implements LoginAuthorizer {

    @Override
    public boolean authorizeUser(Context c, String username, String password, Callbacks loginCallbacks) {

        Log.i("userdata", username);
        Log.i("userdata", password);
        loginCallbacks.OnUserAuthorized(null);

        return true;
    }

    @Override
    public boolean isAdmin(Context c, String username, String password, Callbacks loginCallbacks) {
        return false;
    }
}
