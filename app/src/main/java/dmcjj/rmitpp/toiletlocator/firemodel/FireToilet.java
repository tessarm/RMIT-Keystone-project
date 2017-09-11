package dmcjj.rmitpp.toiletlocator.firemodel;

import android.graphics.Bitmap;
import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmcjj.rmitpp.toiletlocator.model.FirebaseMetaData;

/**
 * Created by A on 4/09/2017.
 */

public class FireToilet
{
    public static final String KEY_NAME = "name";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String KEY_DISABLED = "disabled";
    public static final String KEY_MALE = "male";
    public static final String KEY_FEMALE = "female";
    public static final String KEY_UNISEX = "unisex";
    public static final String KEY_INDOOR = "indoor";


    public static class ClientBuilder
    {
        public String name;
        public double lat;
        public double lng;
        public boolean isMale = true;
        public boolean isFemale = true;
        public boolean isDisabled = false;
        public boolean isUnisex = false;
        public boolean isIndoor = true;

        public void set(Map<String, Object> map){
            name = (String)map.get(KEY_NAME);
            lat = (double)map.get(KEY_LAT);
            lng = (double)map.get(KEY_LNG);
            isMale = (boolean)map.get(KEY_MALE);
            isFemale = (boolean)map.get(KEY_FEMALE);
            isDisabled = (boolean)map.get(KEY_DISABLED);
            isUnisex = (boolean)map.get(KEY_UNISEX);
            isIndoor = (boolean)map.get(KEY_INDOOR);

        }

        public Map<String, Object> get(){
            Map<String, Object> map = new HashMap<>();
            map.put(KEY_NAME, name);
            map.put(KEY_LAT, lat);
            map.put(KEY_LNG, lng);
            map.put(KEY_MALE, isMale);
            map.put(KEY_FEMALE, isFemale);
            map.put(KEY_DISABLED, isDisabled);
            map.put(KEY_UNISEX, isUnisex);
            map.put(KEY_INDOOR, isIndoor);

            return map;
        }

    }


}
