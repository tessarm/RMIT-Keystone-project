package dmcjj.rmitpp.toiletlocator.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmcjj.rmitpp.toiletlocator.R;
import dmcjj.rmitpp.toiletlocator.model.TestOb;
import dmcjj.rmitpp.toiletlocator.test.TestApi;
import dmcjj.rmitpp.toiletlocator.test.TestHThread;
import dmcjj.rmitpp.toiletlocator.test.TestHandler;
import dmcjj.rmitpp.toiletlocator.test.TestThread;

/**
 * Created by A on 21/08/2017.
 */

public class TestAct extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        TestHThread testHThread = new TestHThread(new TestHThread.Interface() {

        });

        testHThread.start();

        testHThread.countMultiplesBetween(1, 10000000);
    }




}
