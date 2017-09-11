package dmcjj.rmitpp.toiletlocator.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dmcjj.rmitpp.toiletlocator.activity.TestAct;

/**
 * Created by A on 30/08/2017.
 */

public class TestThread implements Runnable
{
    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    };

    private Handler uiHandler;


    public TestThread(Handler uiHandler) {
        this.uiHandler = uiHandler;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();

        List<Integer> nums = new LinkedList<>();

        for(int i=0;i < 10000000; i++)
        {
            if(i % 7 == 0)
                nums.add(i);
        }
        long delta = System.currentTimeMillis() - start;
        Message msg = uiHandler.obtainMessage();
        msg.obj = "loop took " + delta + " millis to complete. There are " + nums.size() + " % 7";
        uiHandler.sendMessage(msg);



    }
}
