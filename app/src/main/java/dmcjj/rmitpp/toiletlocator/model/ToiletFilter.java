package dmcjj.rmitpp.toiletlocator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by derrickphung on 25/9/17.
 */

public class ToiletFilter {
    private static final int MALE = 0;
    private static final int FEMALE = 1;
    private static final int UNISEX = 2;
    private static final int DISABLED = 3;
    private static final int INDOOR = 4;

    private boolean[] filter = new boolean[5];
    private List<Integer> indexTrue = new ArrayList<>();

    //public boolean disabled;
    //public boolean female;
    //public boolean male;
    //public boolean unisex;
    //public boolean indoor;
    //public boolean noFilter;

    public ToiletFilter(ArrayList<String> filterResults) {

        for (String i : filterResults) {
            switch (i) {
                case "Disabled": {
                    filter[DISABLED] = true;
                    break;
                }
                case "Female": {
                    filter[FEMALE] = true;
                    break;
                }
                case "Male": {
                    filter[MALE] = true;
                    break;
                }
                case "Unisex": {
                    filter[UNISEX] = true;
                    break;
                }
                case "Indoor Only": {
                    filter[INDOOR] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < filter.length; i++) {
            if (filter[i])
                indexTrue.add(i);

        }


    }

    public boolean matches(ToiletValues toilet) {
        boolean[] compare = new boolean[5];
        compare[MALE] = toilet.isMale();
        compare[FEMALE] = toilet.isFemale();
        compare[UNISEX] = toilet.isUnisex();
        compare[DISABLED] = toilet.isDisabled();
        compare[INDOOR] = toilet.isIndoor();

        for(Integer index : indexTrue){
            if(compare[index] == false)
                return false;
        }
        return true;
    }
}
