package com.tuanbaol.myapplication.util;

import com.tuanbaol.myapplication.constant.CommonConstant;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Util {
    private final static String UTF8 = CommonConstant.CHARSET_UTF8;
    private final static Base64 BASE64 = new Base64();

    public static String encode(String src) {
        return src == null ? null : BASE64.encodeAsString(getBytes(src));
    }

    public static String decode(String encoded) {
        try {
            return encoded == null ? null : new String(BASE64.decode(encoded), UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decode base64 failed,encoded:" + encoded, e);
        }
    }

    private static byte[] getBytes(String src) {
        try {
            return src == null ? null : src.getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("get bytes failed,src:" + src, e);
        }

    }
}
