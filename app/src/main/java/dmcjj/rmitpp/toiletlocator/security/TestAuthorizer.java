package dmcjj.rmitpp.toiletlocator.security;

import android.content.Context;

/**
 * Created by derrickphung on 10/8/17.
 */

public class TestAuthorizer implements LoginAuthorizer {


    @Override
    public boolean authorizeUser(Context c, String username, String password) {
        return true;
    }

    @Override
    public boolean isAdmin(Context c, String username, String password) {
        return false;
    }
}
