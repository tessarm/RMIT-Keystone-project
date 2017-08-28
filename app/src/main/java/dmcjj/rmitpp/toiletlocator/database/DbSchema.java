package dmcjj.rmitpp.toiletlocator.database;

/**
 * Created by A on 14/08/2017.
 */

public interface DbSchema
{
    String TOILET_TB = "TOILET_TABLE";
    String LAT = "lat";
    String LON = "lon";
    String FLAGS = "flags";

    String CREATE_TOILET = "CREATE TABLE " + TOILET_TB + "(" + "_id INTEGER, "  +
            LAT + " DOUBLE, " +
            LON + " DOUBLE, " +
            FLAGS + " INTEGER" + ")";


    String PHOTOS_TB = "PHOTOS_TABLE";
    String TOILET_ID = "toilet_id";
    String URL = "url";

    String CREATE_PHOTOS = "CREATE TABLE " + PHOTOS_TB + "(_id INTEGER, " +
            TOILET_ID + " INTEGER, " +
            URL + " STRING" + ")";





}
