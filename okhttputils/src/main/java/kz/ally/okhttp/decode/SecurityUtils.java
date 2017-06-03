package kz.ally.okhttp.decode;

import java.io.UnsupportedEncodingException;

/**
 * 作者：陈磊
 * 日期：16/3/1
 */
public class SecurityUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 加密
     * @param source
     * @param rc4Password
     * @return
     */
    public static String rc4Encrypt(Object source, String rc4Password) {
        String encrypt = "";
        try {
            encrypt = Base64.encode(RC4.RC4encrypt(rc4Password.getBytes(DEFAULT_CHARSET),
                    String.valueOf(source).getBytes(DEFAULT_CHARSET)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt.replaceAll("/", "_").replaceAll("\\+", "-").replaceAll("=", "~");
    }

    /**
     * 解密
     * @param encryptStr
     * @param rc4Password
     * @return
     */
    public static String rc4Decrypt(String encryptStr, String rc4Password) {
        encryptStr = encryptStr.replaceAll("_", "/").replaceAll("-", "+").replaceAll("\\~", "=");
        String decrypt = "";
        try {
            decrypt = new String(RC4.RC4encrypt(rc4Password.getBytes(DEFAULT_CHARSET),
                    Base64.decode(encryptStr)), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decrypt;
    }
}
