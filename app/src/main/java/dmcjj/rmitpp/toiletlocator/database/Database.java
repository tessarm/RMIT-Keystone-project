package dmcjj.rmitpp.toiletlocator.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 31/08/2017.
 */

public class Database
{
    public static DatabaseReference getUserRef(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
    }



    public static void putToilet(Toilet t)
    {
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("toilets");

        data.push().setValue(t);
    }

    public static void makeAdmin(String email){

    }
}
