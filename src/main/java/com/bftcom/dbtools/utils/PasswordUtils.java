package com.bftcom.dbtools.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class PasswordUtils {

    private static final Map _algorithm = new HashMap();

    public static interface Algorithm {
        String encodePassword(String login, String password);
        String decodePassword(String password);
        String getVersion();
        Algorithm getCommon(Algorithm a);
    }

    private abstract static class AbstractAlgorithm implements Algorithm {
        private final String version;
        protected AbstractAlgorithm(String version) {
            this.version = version;
            _algorithm.put(version, this);
        }

        public String getVersion() {
            return version;
        }

        public Algorithm getCommon(Algorithm a) {
            return getVersion() == null ? a : this;
        }
    }


    private static Algorithm ALGORITHM_DECREMENT = new AbstractAlgorithm("ver4") {
        public String encodePassword(String login, String password) {
            if (password == null)
                password = "";
            return _encodePassword(password);
        }

        private String _encodePassword(String password) {
            StringBuffer sb = new StringBuffer(password);
            sb = sb.reverse();
            for (int i = 0; i < sb.length(); i++) {
                int ii = (int) sb.charAt(i);
                ii--;
                sb.setCharAt(i, (char) ii);
            }
            return sb.toString();
        }

        public String decodePassword(String password) {
            StringBuffer sb = new StringBuffer(password);
            sb = sb.reverse();
            for (int i = 0; i < sb.length(); i++) {
                int ii = (int) sb.charAt(i);
                ii++;
                sb.setCharAt(i, (char) ii);
            }

            return sb.toString();
        }

        public Algorithm getCommon(Algorithm a) {
            return a;
        }
    };

    private static Algorithm ALGORITHM_MD5 = new AbstractAlgorithm("ver3") {
        public String encodePassword(String login, String password) {
            if (password == null)
                password = "";
            return SecurityUtil.MD5Hash(password);
        }


        public String decodePassword(String password) {
            throw new IllegalStateException("There is unsupported algorithm maybe used");
        }
    };

    public static Algorithm getAlgorithm(String version) {
        return (Algorithm) _algorithm.get(version);
    }

    public static String getVersion(String password) {
        return password == null || password.length() < 5 || password.charAt(4) != ':' ? null : password.substring(0, 4);
    }
}
