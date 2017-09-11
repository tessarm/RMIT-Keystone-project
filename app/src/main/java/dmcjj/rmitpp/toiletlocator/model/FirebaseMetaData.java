package dmcjj.rmitpp.toiletlocator.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by A on 4/09/2017.
 */

public class FirebaseMetaData
{
    public String owner;
    public Map<String, String> timestamp;

    public String getOwner(){
        return owner;
    }

    public FirebaseMetaData setOwner(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null)
            return this;
        owner = currentUser.getUid();
        return this;
    }
    public FirebaseMetaData setTimestamp(){
        timestamp = ServerValue.TIMESTAMP;
        return this;
    }


}
