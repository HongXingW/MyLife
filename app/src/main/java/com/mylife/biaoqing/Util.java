package com.mylife.biaoqing;

import android.content.res.Resources;
import android.util.TypedValue;
/**
 * Created by whx on 2015/10/22.
 */


public class Util {

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }

}
