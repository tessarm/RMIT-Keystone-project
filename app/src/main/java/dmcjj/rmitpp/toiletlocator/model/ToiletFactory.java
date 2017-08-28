package dmcjj.rmitpp.toiletlocator.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by A on 18/08/2017.
 */

public class ToiletFactory
{
    private static String JK_LOCNAME = "location_name";
    private static String JK_LAT = "lat";
    private static String JK_LON = "lon";
    private static String JK_DISAB = "disabled";
    private static String JK_MALE = "male";
    private static String JK_FEMALE = "female";
    private static String JK_OTHER = "other";
    private static String JK_INOUT = "indoor_outdoor";

    public  static Toilet createToilet(JSONObject object){

        try {
            String location = object.getString(JK_LOCNAME);
            double lat = object.getDouble(JK_LAT);
            double lon = object.getDouble(JK_LON);
            boolean disab = object.getBoolean(JK_DISAB);
            boolean male = object.getBoolean(JK_MALE);
            boolean female = object.getBoolean(JK_FEMALE);
            String other = object.getString(JK_OTHER);


            Toilet t = new Toilet(location, lat, lon, disab, male, female);

            return t;

        }
        catch (JSONException e){
            return Toilet.NULL;
        }

    }


}
