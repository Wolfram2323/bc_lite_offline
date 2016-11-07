package com.bftcom.dbtools.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * Утилитарные методы безопасности
 * User: ruban
 * Date: 17.10.2007
 * Time: 15:45:33
 */
public class SecurityUtil {

    public static String MD5Hash(String str) {
        byte[] r = Charset.forName("Cp1251").encode(str).array();
        return MD5Hash(r);
    }

    public static String MD5Hash(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] t = md.digest(data);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < t.length; i++) {
            s.append(Integer.toHexString(t[i] + 512).substring(1).toUpperCase());
        }
        return s.toString();
    }
}
