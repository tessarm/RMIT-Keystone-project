package dmcjj.rmitpp.toiletlocator.server_model;

/**
 * Created by A on 9/09/2017.
 */

public abstract class ServiceObject
{
    public static final String TYPE_TOILET_VIEW = "toilet_view";


    public String service_type;

    public ServiceObject(String serviceType){
        this.service_type = serviceType;
    }
}
