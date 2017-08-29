package dmcjj.rmitpp.toiletlocator.security;

import android.content.Context;

/**
 * Created by derrickphung on 10/8/17.
 */

public interface LoginAuthorizer
{
    interface Callbacks{
        void OnUserAuthorized(UserInfo userInfo);
        void OnUserDenied();
    }

    boolean authorizeUser(Context c, String username, String password, Callbacks loginCallbacks);
    boolean isAdmin(Context c, String username, String password, Callbacks loginCallbacks);
}
