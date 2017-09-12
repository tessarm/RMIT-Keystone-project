package dmcjj.rmitpp.toiletlocator;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by A on 12/09/2017.
 */

public class DbRef
{
    public static final String DBREF_TOILETS_DATA = "toilets/data";

    public static final String DBREF_GEOFIRE_TOILETS = "geofire/toilets";
    public static final String DBREF_LOGIN = "server/login";
    public static final String DBREF_SERVICE_QUEUE = "server/object_queue";
    public static final String DBREF_USERPROF = "users/%s";

    private static final String DBREF_TOILET_COMMENTS = DBREF_TOILETS_DATA + "/%s/comments";


    public static final FirebaseDatabase DATABASE = FirebaseDatabase.getInstance();
    public static final GeoFire GEOFIRE_TOILETS = new GeoFire(DATABASE.getReference(DBREF_GEOFIRE_TOILETS));


    public static final String getRefToiletComments(String toiletKey){
        return String.format(DBREF_TOILET_COMMENTS, toiletKey);
    }


    public static String getUserRef(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return String.format(DBREF_USERPROF, user.getUid());
    }



}
