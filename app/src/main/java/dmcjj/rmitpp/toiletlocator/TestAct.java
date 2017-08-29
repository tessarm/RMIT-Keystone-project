package dmcjj.rmitpp.toiletlocator;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.LocationSource;

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

    }





}
