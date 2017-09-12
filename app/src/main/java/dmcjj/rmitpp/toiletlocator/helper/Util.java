package dmcjj.rmitpp.toiletlocator.helper;

/**
 * Created by A on 21/08/2017.
 */

public class Util
{
    public static boolean isNull(Object... objects){
        for(int i=0; i < objects.length; i++)
            if(objects[i] == null)
                return true;
        return false;
    }
}
