package dmcjj.rmitpp.toiletlocator.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import dmcjj.rmitpp.toiletlocator.TestAct;

/**
 * Created by A on 30/08/2017.
 */

public class TestThread extends HandlerThread
{
    public Handler handler;

    private Handler uiHandler;


    public TestThread(Handler uiHandler) {
        super("TestName");
        this.uiHandler = uiHandler;

    }

    @Override
    public synchronized void start() {
        super.start();
        Log.i("thread", "Started");
        handler = new Handler(getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("thread", Thread.currentThread().getName());
                Message m = uiHandler.obtainMessage();
                m.what = TestAct.WHAT_LOG;
                m.obj = "Hello Handler";
                uiHandler.sendMessage(m);
            }
        };



    }
}
