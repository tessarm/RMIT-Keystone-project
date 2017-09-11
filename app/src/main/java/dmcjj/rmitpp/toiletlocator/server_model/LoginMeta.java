package dmcjj.rmitpp.toiletlocator.server_model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by A on 9/09/2017.
 */

public class LoginMeta
{
    public Map<String, String> timestamp = ServerValue.TIMESTAMP;
    public String owner;
    public double lat;
    public double lng;

    public LoginMeta(FirebaseUser user){
        this.owner = user.getUid();
    }
}
