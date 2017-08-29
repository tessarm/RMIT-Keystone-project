package dmcjj.rmitpp.toiletlocator.security;

import android.content.Context;

/**
 * Created by derrickphung on 10/8/17.
 */

public class LocalAuthorizer implements LoginAuthorizer
{

    @Override
    public boolean authorizeUser(Context c, String username, String password, Callbacks loginCallbacks) {
        return false;
    }

    @Override
    public boolean isAdmin(Context c, String username, String password, Callbacks loginCallbacks) {
        return false;
    }
}
