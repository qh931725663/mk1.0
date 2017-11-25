package com.haaa.cloudmedical.interceptor.util;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************
 * AES加解密算法
 * 
 * @author arix04
 * 
 */

public class AESUtil {

    private static final String IV_STRING = "16-Bytes--String";
    private static final String KEY       = "1234567890123456";

    public static String encryptAES(String content) throws Exception {
        byte[] byteContent = content.getBytes("UTF-8");
        // 注意，为了能与 iOS 统一
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = KEY.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        // 指定加密的算法、工作模式和填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(byteContent);
        // 同样对加密后数据进行 base64 编码
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(encryptedBytes);
    }

    public static String decryptAES(String content) throws Exception {
        // base64 解码
        Decoder decoder = Base64.getDecoder();
        byte[] encryptedBytes = decoder.decode(content);
        byte[] enCodeFormat = KEY.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
        byte[] initParam = IV_STRING.getBytes();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] result = cipher.doFinal(encryptedBytes);
        return new String(result, "UTF-8");
    }

    /*public static void main(String[] args) throws Exception {
        
         * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
         * 此处使用AES-128-CBC加密模式，key需要为16位。
         
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "147762857";
        System.out.println(cSrc);
        // 加密
        String enString = AES.encryptAES(cSrc);
        System.out.println("加密后的字串是：" + enString);
    
        // 解密
        String DeString = AES.decryptAES(enString);
        System.out.println("解密后的字串是：" + DeString);
    }*/
}