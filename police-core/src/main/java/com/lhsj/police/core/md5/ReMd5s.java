package com.lhsj.police.core.md5;

import com.lhsj.police.core.log.ReLogs;
import com.google.common.base.Strings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReMd5s {

    public static String encode(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return null;
        }
        MessageDigest md;
        byte[] encryptMsg = null;

        try {
            // getting a 'MD5-Instance'
            md = MessageDigest.getInstance("MD5");
            // solving the MD5-Hash
            encryptMsg = md.digest(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            ReLogs.error("No Such Algorithm Exception!", e);
        }

        if (encryptMsg == null || encryptMsg.length <= 0) {
            return null;
        }

        // swap-string for the result
        String swap = "";
        // swap-string for current hex-value of byte
        String byteStr = "";
        StringBuilder strBuf = new StringBuilder();

        for (int i = 0; i <= encryptMsg.length - 1; i++) {

            // swap-string for current hex-value of byte
            byteStr = Integer.toHexString(encryptMsg[i]);

            switch (byteStr.length()) {
                case 1:
                    // if hex-number length is 1, add a '0' before
                    swap = "0" + Integer.toHexString(encryptMsg[i]);
                    break;

                case 2:
                    // correct hex-letter
                    swap = Integer.toHexString(encryptMsg[i]);
                    break;

                case 8:
                    // get the correct substring
                    swap = (Integer.toHexString(encryptMsg[i])).substring(6, 8);
                    break;
                default:
            }
            // appending swap to get complete hash-key
            strBuf.append(swap);
        }
        return strBuf.toString();
    }

}
