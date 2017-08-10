package dmcjj.rmitpp.toiletlocator.security;

import android.content.Context;

/**
 * Created by derrickphung on 10/8/17.
 */

public interface LoginAuthorizer
{
    public boolean authorizeUser(Context c, String username, String password);
    public boolean isAdmin(Context c, String username, String password);
}
