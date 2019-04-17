package com.nuoxin.virtual.rep.api.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 加解密,128位
 * @author tiancun
 * @date 2019-03-18
 */
public class Aes128Util {

    private final static String encoding      = "UTF-8";
    private final static String AES  = "AES";
    private final static String KEY  = "VR_HCP360";

    /**
     * AES加密
     * **/
    public static String encryptAES(String content) {
        if (StringUtil.isEmpty(content)){
            return null;
        }
        byte[] encryptResult = encrypt(content);
        if (encryptResult == null || encryptResult.length == 0){
            return null;
        }
        String encryptResultStr = parseByte2HexStr(encryptResult);
        // BASE64位加密
        encryptResultStr = ebotongEncrypto(encryptResultStr);
        return encryptResultStr;
    }

    /**
     * AES解密
     * @param encryptResultStr
     * @return String
     * **/
    public static String decryptAES(String encryptResultStr) {
        if (StringUtil.isEmpty(encryptResultStr)){
            return null;
        }

        // BASE64位解密
        String decrpt = ebotongDecrypto(encryptResultStr);
        byte[] decryptFrom = parseHexStr2Byte(decrpt);
        byte[] decryptResult = decrypt(decryptFrom);
        if (decryptResult == null || decryptResult.length == 0){
            return null;
        }
        return new String(decryptResult);
    }


    /**
     * 加密字符串
     * */
    public static String ebotongEncrypto(String str) {

        BASE64Encoder base64encoder = new BASE64Encoder();
        String result = str;
        if (str != null && str.length() > 0) {
            try {

                byte[] encodeByte = str.getBytes(encoding);
                result = base64encoder.encode(encodeByte);
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //base64加密超过一定长度会自动换行 需要去除换行符
        return result.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
    }

    /**
     * 解密字符串
     * */
    public static String ebotongDecrypto(String str) {

        BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            byte[] encodeByte = base64decoder.decodeBuffer(str);
            return new String(encodeByte);
        } catch (IOException e) {
            e.printStackTrace();
            return str;
        }
    }



    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @return
     */
    public static byte[] encrypt(String content) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            byte[] byteContent = content.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**解密
     * @param content  待解密内容
     * @return
     */
    public static byte[] decrypt(byte[] content) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance(AES);
            //kgen.init(128, new SecureRandom(AESUtilsPassWordKey.PASSWORD_KEY.getBytes()));
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**将16进制转换为二进制 解密
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {

        if (hexStr.length() < 1){
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制 加密
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        String content = "中文测试";
        String encryptResultStr = encryptAES("dongwei.zhang@naxions.com");
        System.out.println("加密前: "+content);
        System.out.println("加密后: "+encryptResultStr);
        System.out.println("解密后: "+decryptAES(encryptResultStr)); // RTNGRjJBMDRERkJFQTdBRUQ0NjBGNjRENzA5Q0QzOTk=

        System.out.println("解密后: "+decryptAES("RTNGRjJBMDRERkJFQTdBRUQ0NjBGNjRENzA5Q0QzO"));
    }

}
