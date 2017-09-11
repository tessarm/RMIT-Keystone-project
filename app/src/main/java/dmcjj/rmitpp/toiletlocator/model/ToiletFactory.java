package dmcjj.rmitpp.toiletlocator.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by A on 18/08/2017.
 */

public class ToiletFactory
{
    private static String JK_LOCNAME = "name";
    private static String JK_LAT = "lat";
    private static String JK_LON = "lon";
    private static String JK_DISAB = "wheelchair";
    private static String JK_MALE = "male";
    private static String JK_FEMALE = "female";
    //private static String JK_OTHER = "other";
    private static String JK_INOUT = "indoor_outdoor";

    public  static ToiletValues createToilet(JSONObject object){

        try {
            String location = object.getString(JK_LOCNAME);
            double lat = object.getDouble(JK_LAT);
            double lon = object.getDouble(JK_LON);
            boolean disab = object.getString(JK_DISAB).contentEquals("yes");
            boolean male = object.getString(JK_MALE).contentEquals("yes");
            boolean female = object.getString(JK_FEMALE).contentEquals("yes");
            //String other = object.getString(JK_OTHER);


            ToiletValues t = null;// new ToiletValues(location, lat, lon, disab, male, female);
            

            return t;

        }
        catch (JSONException e){
            return ToiletValues.NULL;
        }

    }


}
