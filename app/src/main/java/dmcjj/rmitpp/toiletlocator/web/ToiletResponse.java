package dmcjj.rmitpp.toiletlocator.web;

import dmcjj.rmitpp.toiletlocator.model.ToiletValues;

/**
 * Created by A on 21/08/2017.
 */

public class ToiletResponse
{
    private ToiletValues[] toiletData;

    public ToiletResponse(ToiletValues[] toilets){
        this.toiletData = toilets;
    }


    public ToiletValues[] getToiletData()
    {
        return toiletData;
    }
}
