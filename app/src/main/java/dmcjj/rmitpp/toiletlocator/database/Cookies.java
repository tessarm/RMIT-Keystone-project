package dmcjj.rmitpp.toiletlocator.database;

import android.content.Context;
import android.support.v4.util.ArrayMap;

/**
 * Created by A on 29/08/2017.
 */

public class Cookies
{
    private static ArrayMap<String, Integer> IntMap = new ArrayMap<>();
    private static ArrayMap<String, String> StringMap = new ArrayMap<>();

    public int getInt(String key, int defaultReturn){
        if(!IntMap.containsKey(key))
            return defaultReturn;
        return IntMap.get(key);
    }
    public void setInt(String key, int value){
        IntMap.put(key, value);
    }

    public String getString(String key, String defaultReturn){
        if(!StringMap.containsKey(key))
            return defaultReturn;
        return StringMap.get(key);
    }
    public void setString(String key, String value){
        StringMap.put(key, value);
    }

}
