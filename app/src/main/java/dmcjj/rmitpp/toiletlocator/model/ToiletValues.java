package dmcjj.rmitpp.toiletlocator.model;

import dmcjj.rmitpp.toiletlocator.geo.GeoReadable;
import dmcjj.rmitpp.toiletlocator.helper.Util;

/**
 * Created by A on 10/08/2017.
 */

public class ToiletValues implements GeoReadable
{
    public static final ToiletValues NULL = new ToiletValues();

    private String name;
    private double lat;
    private double lng;
    private boolean disabled;
    private boolean unisex;
    private boolean male;
    private boolean female;
    private boolean indoor;

    public ToiletValues(){
    }


    public ToiletValues(String name, double lat, double lng, boolean disabled, boolean male, boolean female, boolean unisex, boolean indoor){
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.disabled = disabled;
        this.male = male;
        this.female = female;
        this.unisex = unisex;
        this.indoor = indoor;
    }

    public boolean isMale(){
        return male;
    }
    public boolean isFemale(){
        return female;
    }
    public boolean isDisabled(){return disabled;}
    public boolean isUnisex(){return unisex;}
    public boolean isIndoor(){return indoor;}
    public void setMale(boolean b){
        this.male = b;
    }
    public void setFemale(boolean b){
        this.female = b;
    }
    public void setDisabled(boolean b){this.disabled = b;}
    public void setUnisex(boolean b){this.unisex = b;}
    public void setIndoor(boolean b){this.indoor = b;}

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }



    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLng() {
        return lng;
    }

    public void setLat(double lat){
        this.lat = lat;
    }
    public void setLng(double lng){
        this.lng = lng;
    }
    public static ToiletValues create(String name, double lat, double lng, boolean isMale, boolean isFemale, boolean isUniSex, boolean isDisabled, boolean indoor)
    {
        return new ToiletValues(name, lat, lng, isDisabled, isMale, isFemale, isUniSex, indoor);
    }



    @Override
    public String toString(){
        return String.format("name=%s,lat=%f,lng=%f", name, lat, lng);
    }
}
