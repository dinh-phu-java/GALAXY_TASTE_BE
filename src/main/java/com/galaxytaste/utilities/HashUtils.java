package com.galaxytaste.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class HashUtils {
    public static String hashFileName(String fileName){
        if (fileName.equals(""))
            return "";

        String postFix=fileName.substring(fileName.indexOf('.'));
        fileName+=fileName+(new Date());
        StringBuilder sb=null;
        try {

            MessageDigest md =MessageDigest.getInstance("SHA-256");
            md.update(fileName.getBytes());
            byte[] mdArray= md.digest();
            sb= new StringBuilder(mdArray.length*2);

            for (byte b: mdArray){
                int v= b & 0xff;
                if (v < 16){
                    sb.append('0');
                }
                sb.append(Integer.toHexString(v));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sb.toString()+postFix;

    }
}
