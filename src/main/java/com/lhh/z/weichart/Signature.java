package com.lhh.z.weichart;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signature {
	public static final String sha1(String data) {
        MessageDigest digest = null;
        if (digest == null) {
            try {
            	digest = MessageDigest.getInstance("SHA-1");
            } catch (NoSuchAlgorithmException e) {
            }
        }
        // Now, compute hash.
        digest.update(data.getBytes());
        return encodeHex(digest.digest());
    }
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;

        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }
    public static void main(String[] args) {
    	String str = Signature.sha1("77");
    	System.out.println(str);
	}
}
