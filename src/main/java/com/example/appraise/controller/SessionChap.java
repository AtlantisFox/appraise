package com.example.appraise.controller;

import com.example.appraise.model.ArUserSecure;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;

/**
 * 负责处理登陆过程中，Session和challenge对应管理。
 */
public class SessionChap {
    private static final String SESSION_ATTR = "$SecureChallenge$";
    private final HttpSession session;
    private static final SecureRandom random = new SecureRandom();

    public SessionChap(HttpSession session) {
        this.session = session;
    }

    public boolean check(ArUserSecure user, String password) {
        byte[] challenge = (byte[]) session.getAttribute(SESSION_ATTR);
        if (challenge == null)
            return false;
        return true;
    }

    private static String byteToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i];
            hexChars[i*2] = hexArray[v >>> 4];
            hexChars[i*2+1] = hexArray[v & 0xf];
        }
        return new String(hexChars);
    }

    public String generateChallenge() {
        byte[] challenge = new byte[16];
        random.nextBytes(challenge);
        session.setAttribute(SESSION_ATTR, challenge);
        return byteToHex(challenge);
    }
}
