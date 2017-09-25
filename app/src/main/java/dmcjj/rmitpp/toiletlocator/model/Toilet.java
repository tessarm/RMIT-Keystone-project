package dmcjj.rmitpp.toiletlocator.model;

import android.support.annotation.NonNull;

import java.util.Map;

/**
 * Created by A on 5/09/2017.
 */

public class Toilet
{
    public ToiletMetaData metadata;
    public ToiletValues value;
    public ToiletStats stats;
    public Map<String, String> images;


    @Override
    public String toString(){
        return String.format("metadata={%s},value={%s}", metadata, value);
    }

    public String getLatLng()
    {
        return String.format("%f,%f", value.getLat(), value.getLng());
    }

}
