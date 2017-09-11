package dmcjj.rmitpp.toiletlocator.test;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by A on 10/09/2017.
 */

public class TestApi
{
    private TestHandler mTestHandler;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            mTestHandler.onGo((String)msg.obj);
        }
    };


    public void register(TestHandler handler){
       this.mTestHandler = handler;
    }

    public void load(){

        TestThread thread = new TestThread(mHandler);
        Thread t = new Thread(thread);
        t.start();
    }

}
