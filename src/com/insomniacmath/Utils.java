package com.insomniacmath;


public class Utils {

    public  static String round(float i) {
        if (i % 1 == 0)
            return Integer.toString((int) i);
        else
            return Float.toString(i);
    }

}