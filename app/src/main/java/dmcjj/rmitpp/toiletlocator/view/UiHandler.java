package dmcjj.rmitpp.toiletlocator.view;

import com.google.firebase.database.DataSnapshot;

import dmcjj.rmitpp.toiletlocator.model.Toilet;

/**
 * Created by A on 8/09/2017.
 */

public interface UiHandler
{
    boolean onToiletClicked(DataSnapshot toilet);
    void onCurrentToiletChanged(DataSnapshot toilet);
}
