package com.pujun.apiaccessinterfacesdk.utils;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * 加密签名类
 * @author pujun
 */
public class SignUtil {
    public static String getSign(String body, String secretKey) {
        String content = body + "." + secretKey;
        return DigestUtil.md5Hex(content);
    }
}
