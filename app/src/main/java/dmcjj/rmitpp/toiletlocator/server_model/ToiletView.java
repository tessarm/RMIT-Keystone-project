package dmcjj.rmitpp.toiletlocator.server_model;

/**
 * Created by A on 9/09/2017.
 */

public class ToiletView extends ServiceObject
{
    public String toilet_id;

    public ToiletView(String toiletId) {
        super(TYPE_TOILET_VIEW);
        this.toilet_id = toiletId;
    }
}
