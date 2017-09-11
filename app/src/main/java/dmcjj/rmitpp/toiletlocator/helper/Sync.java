package dmcjj.rmitpp.toiletlocator.helper;

import android.util.Log;

/**
 * Created by A on 6/09/2017.
 */

public class Sync
{
    private final String[] keys;
    private final boolean[] state;
    private Runnable runnable;
    private boolean hasRun = false;

    private Sync(String[] keys, Runnable runnable){
        this.keys = keys;
        this.state = new boolean[keys.length];
        this.runnable = runnable;
    }

    private void checkState(){
        for(int i=0; i < state.length; i++)
            if(state[i] == false)
                return;
        if(!hasRun){
            runnable.run();
            hasRun = true;
        }
    }

    public void post(String value){
        if(hasRun)
            return;
        Log.i("Sync.post", value);
        for(int i=0; i < keys.length; i++)
            if(keys[i].contentEquals(value))
                state[i] = true;
        checkState();
    }

    public static Builder create(String... keys){
        return new Builder(keys);
    }

    public static class Builder{
        private String[] keys;
        public Builder(String[] keys){
            this.keys = keys;
        }

        public Sync build(Runnable runnable){
            return new Sync(keys, runnable);
        }
    }


}
