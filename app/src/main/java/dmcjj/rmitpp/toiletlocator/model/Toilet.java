package dmcjj.rmitpp.toiletlocator.model;

import dmcjj.rmitpp.toiletlocator.util.StringUtil;

/**
 * Created by A on 10/08/2017.
 */

public class Toilet
{
    public static final Toilet NULL = new Toilet();
    private static String OUTDOOR = "Outdoor";
    private static String INDOOR = "Indoor";

    private String name;
    private double lat;
    private double lon;
    private boolean disabled;
    private boolean male;
    private boolean female;

    private String indoorOutdoor;

    private Toilet(){
        name = StringUtil.NULL;
    }


    Toilet(String name, double lat, double lon, boolean disabled, boolean male, boolean female){
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.disabled = disabled;
        this.male = male;
        this.female = female;

    }


    public String getName() {
        return name;
    }
}