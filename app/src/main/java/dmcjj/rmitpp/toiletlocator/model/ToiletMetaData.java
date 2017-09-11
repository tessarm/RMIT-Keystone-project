package dmcjj.rmitpp.toiletlocator.model;

/**
 * Created by A on 5/09/2017.
 */

public class ToiletMetaData
{
    public float rating;
    public String owner;
    public long timestamp;

    @Override
    public String toString(){
        return String.format("owner=%s,timestamp=%d,rating=%f", owner, timestamp, rating);
    }

}
