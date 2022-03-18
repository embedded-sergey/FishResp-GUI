package com.fishrespbase.components;

/**
 * Created by R7Bishop on 13.03.2017.
 */
public class TextInputCheckers {
    public static boolean checkTime(String time){
        if (time.length()==6) {
            if (time.charAt(0) < '0' && time.charAt(0) > '2')
                return false;
            else if (time.charAt(0) != '2') {
                if (time.charAt(0) < '0' && time.charAt(0) > '9')
                    return false;
            }
            else
            if (time.charAt(0) < '0' && time.charAt(0) > '4')
                return false;

            for (int i = 0; i < 2; i++) {
                if (time.charAt(i + 2) != ':')
                    return false;
                if (time.charAt(i + 3) < '0' && time.charAt(i + 3) > '5')
                    return false;
                if (time.charAt(i + 4) < '0' && time.charAt(i + 4) > '9')
                    return false;
            }
            return true;
        }
        else
            return false;
    }

    public static boolean checkInt(String text) {
        if (text.length()==0)
            return true;

        for (int i = 0; i < text.length(); i++)
            if ((text.charAt(i)<48 || text.charAt(i)>57))
                return false;
        return true;
    }

    public static boolean checkFloat(String text) {
        if (text.length()==0)
            return true;

        int dotCount = 0;
        for (int i = 0; i < text.length(); i++)
            if ((text.charAt(i)<48 || text.charAt(i)>57)
                    &&  text.charAt(i)!='.')
                return false;
            else
            if (text.charAt(i)==' ')
                return false;
            else
            if (text.charAt(i)=='.')
                if (dotCount<1)
                    dotCount++;
                else
                    return false;

        return true;
    }

    public static boolean checkInt(String text, int min, int max) {
        if (text.length()==0)
            return true;
        if (!checkInt(text))
            return false;

        int tmp = Integer.parseInt(text);

        if (min<=tmp && tmp<=max)
            return true;
        else
            return false;
    }

    public static boolean checkFloat(String text, float min, float max) {
        if (text.length()==0)
            return true;
        if (text.length()==1)
            if (text.charAt(0) == '.')
                return false;
        if (!checkFloat(text))
            return false;

        float tmp = Float.parseFloat(text);

        if (min<=tmp && tmp<=max)
            return true;
        else
            return false;
    }
}
