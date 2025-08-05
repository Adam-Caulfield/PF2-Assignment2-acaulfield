package utils;

import java.util.ArrayList;

public class SpectralTypeUtility {

    private static ArrayList<Character> spectralTypes = new ArrayList<>(){{
        add('O');
        add('B');
        add('A');
        add('F');
        add('G');
        add('K');
        add('M');


    }};


    public static boolean isValidSpectralType(char type) {
        //must not be case sensitive
        for (char sType:spectralTypes){
            if (sType==type) {
                return true;
            }
        }
        return false;
    }
}
