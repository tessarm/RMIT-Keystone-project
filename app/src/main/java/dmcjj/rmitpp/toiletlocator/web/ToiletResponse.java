package dmcjj.rmitpp.toiletlocator.web;

import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 21/08/2017.
 */

public class ToiletResponse
{
    private Toilet[] toiletData;

    public ToiletResponse(Toilet[] toilets){
        this.toiletData = toilets;
    }


    public Toilet[] getToiletData()
    {
        return toiletData;
    }
}
