package dmcjj.rmitpp.toiletlocator.test;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by A on 10/09/2017.
 */

public class TestHThread extends HandlerThread
{
    public interface Interface{

    }

    private Handler mHandler;
    private TestHThread.Interface anInterface;

    private Handler mUiHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    public TestHThread(Interface anInterface) {
        super("tesththread");
        this.anInterface = anInterface;
    }


    @Override
    public synchronized void start() {
        super.start();

        //the thread handler
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int multiple = msg.arg1;
                int maxNum = msg.arg2;

                int count = 0;
                for(int i=0; i < maxNum; i++)
                    if(i % multiple == 0)
                        ++count;
                //post result to ui thread
                Log.d("testthread", String.format("multiple:%d|max:%d|count:%d", multiple, maxNum, count));
                countMultiplesBetween(multiple+1, maxNum);

            }
        };

    }
    //can be called on any thread
    public void countMultiplesBetween(int multiple, int maxNumber){

        Message msg = mHandler.obtainMessage();
        msg.arg1 = multiple;
        msg.arg2 = maxNumber;

        mHandler.sendMessage(msg);
    }


}
