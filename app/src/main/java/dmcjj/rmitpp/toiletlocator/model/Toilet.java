package dmcjj.rmitpp.toiletlocator.model;

import android.support.annotation.NonNull;

/**
 * Created by A on 5/09/2017.
 */

public class Toilet
{
    public ToiletMetaData metadata;
    public ToiletValues value;
    public ToiletStats stats;

    @Override
    public String toString(){
        return String.format("metadata={%s},value={%s}", metadata, value);
    }

}
