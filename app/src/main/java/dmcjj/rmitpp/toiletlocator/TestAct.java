package dmcjj.rmitpp.toiletlocator;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dmcjj.rmitpp.toiletlocator.model.TestOb;
import dmcjj.rmitpp.toiletlocator.model.Toilet;
import dmcjj.rmitpp.toiletlocator.model.ToiletFactory;
import dmcjj.rmitpp.toiletlocator.test.TestThread;
import dmcjj.rmitpp.toiletlocator.web.OnToiletListener;
import dmcjj.rmitpp.toiletlocator.web.ToiletResponse;
import dmcjj.rmitpp.toiletlocator.web.ToiletTask;

/**
 * Created by A on 21/08/2017.
 */

public class TestAct extends AppCompatActivity
{
    public static final int WHAT_LOG = 78;

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case WHAT_LOG:{
                    String hey = (String)msg.obj;
                    Log.i("thread", hey);
                    Log.i("thread", Thread.currentThread().getName());
                }break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        TestThread test = new TestThread(mHandler);
        test.start();

        Log.i("thread", Thread.currentThread().getName());
        test.handler.sendEmptyMessage(WHAT_LOG);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();




        final FirebaseAuth mAuth = FirebaseAuth.getInstance();



        mAuth.signInWithEmailAndPassword("project@gmail.com", "password").
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();


                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference userRef = db.getReference().child("users").child("posts").child(user.getUid());
                            //DatabaseReference posts = userRef.child("posts");





                            TestOb ob = new TestOb("Milo", "Milo is the best dog + scamper", 394, System.currentTimeMillis());


                            userRef.push().setValue(ob, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Log.d("firebase", "Data could not be saved " + databaseError.getMessage());
                                    } else {
                                        Log.d("firebase","Data saved successfully.");
                                    }
                                }
                            });




                        }
                        else
                        {
                            Toast.makeText(TestAct.this, "Error Logging in", Toast.LENGTH_SHORT).show();
                        }



                    }
                });



    }





}
