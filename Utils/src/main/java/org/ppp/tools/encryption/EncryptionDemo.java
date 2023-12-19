package org.ppp.tools.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;

/**
 * @author Whoopsunix
 */
public class EncryptionDemo {
    /**
     * md5加密
     *
     * @param str 明文
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] hash = messageDigest.digest(str.getBytes());
            return trans_byte_2_str(hash);
        } catch (Exception e) {

        }
        return "";
    }


    /**
     * AES加密
     *
     * @param str     明文
     * @param MODE    加密模式 ECB CBC CTR CFB OFB
     * @param PADDING 填充方式 PKCS5Padding、NoPadding
     * @param KEY     密钥
     * @param IV      偏移量
     * @return 返回值为空时，代表加密失败，可能是用户构建出错（如ECB模式明文长度不为16的倍数）
     * @throws Exception
     */
    public static String AES(String str, String MODE, String PADDING, String KEY, String IV) throws Exception {
        try {
            String TRANSFORMATION = "AES/" + MODE + "/" + PADDING;

            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            if (MODE.equals("ECB")) {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            }
            return trans_byte_2_str(cipher.doFinal(str.getBytes()));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "";
    }

    /**
     * DES加密
     *
     * @param str     明文
     * @param MODE    加密模式 ECB CBC CTR CFB OFB
     * @param PADDING 填充方式 PKCS5Padding、NoPadding
     * @param KEY     密钥
     * @param IV      偏移量
     * @return 返回值为空时，代表加密失败，可能是用户构建出错（如key的长度不为8的倍数）
     * @throws Exception
     */
    public static String DES(String str, String MODE, String PADDING, String KEY, String IV) throws Exception {
        try {
            String TRANSFORMATION = "DES/" + MODE + "/" + PADDING;

            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "DES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(IV.getBytes());

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            if (MODE.equals("ECB")) {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
            }
            return trans_byte_2_str(cipher.doFinal(str.getBytes()));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return "";
    }

    public static String encrypt(byte[] key, String str, String TRANSFORMATION) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "DES");
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return trans_byte_2_str(cipher.doFinal(str.getBytes()));
    }

    /**
     * sha1 加密
     *
     * @param str 明文
     * @return
     */
    public static String sha1(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hash = messageDigest.digest(str.getBytes());
            return trans_byte_2_str(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // byte[] -> str
    public static String trans_byte_2_str(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
