package com.haaa.cloudmedical.common.util.safety;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.stereotype.Service;

import com.haaa.cloudmedical.common.util.InfoJson;
import com.haaa.cloudmedical.common.util.redis.RedisCacheUtil;


@Service
public class TokenUtils{
	
	//
	public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDomnADEQoFDFDd2wNtYOgIlLUO9fgv2dhslGxV"+
"jaBfWfhsHWDb8IZM5VmH6Yo9FqYgHDVfN9Yhdav9oGiO/XahihlxaXTgeHh1fFYHYkGoArSsxuy5"+
"wtBXK7y3ZLtIYDJ6XdAxwE9MW/D1aPhhN/M3kmSP4ZjiaRJbOiC2/r6lGQIDAQAB";
	
	
	public static String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOiacAMRCgUMUN3bA21g6AiUtQ71"+
"+C/Z2GyUbFWNoF9Z+GwdYNvwhkzlWYfpij0WpiAcNV831iF1q/2gaI79dqGKGXFpdOB4eHV8Vgdi"+
"QagCtKzG7LnC0FcrvLdku0hgMnpd0DHAT0xb8PVo+GE38zeSZI/hmOJpEls6ILb+vqUZAgMBAAEC"+
"gYEA1OM0jbn0n7Yr9UHUmzFMbT4dkLnTE4igqpAVrx/Wni6sTdd9pQ84+/wVu20mFs858L2go6tp"+
"EmqupY1A5U5aWbRFkTzbiLEwmbMyz/VWbZBjcmpHYyQa4q1KSZjOlDUC9dSnDaeeWb/LT5PgxXE/"+
"uUYEwlzD+HFcoYTm7VsY42ECQQD6VrSyAN8w9zqfzHLWX/+6qLQWJzwtJ8xohNzb8Biaf9UWa+h2"+
"iDrAYpkknxTf9nCgoAECfSrKixR06pTLvB59AkEA7d0OMJ3rDHtr9fayvLsSPhbvx7vdU0qrYtF2"+
"OXF6g6i/uYeqj3TChZ9zs5QdCaYGTIVzWpQcY2mrkp4ICWEXzQJBAMVuOFfiRCOz93Irpa3YTDED"+
"9/M4wkbCin0ru5thgaXnh0CFnfJNgjhUCn9NTwNKsoybY/B4kpTmlx2WG/+Jv2kCQFUgDVn8yge3"+
"i9WEqka8tDSgRMO4SebrDBbbW3IHWI8RrzuYf8PDR3JGtEOKODIhxpNQFw5NfzNct9hK0hl5M+kC"+
"QDnTTnzxmpaM0k7WGZ+ulsBzcMg5XT+WkeAxDVbl5Y99V8kgFbrI7S2+Ert0PBcH0M77QcWmy1SV"+
"Mfnsx2dnzOA=";
	
	//
	static long exp_time= 7200;
	
	static TimeUnit exp_unit=TimeUnit.SECONDS;
	
	@Resource
	private  RedisCacheUtil redisCacheUtil;
	

	public  String tokenGenerator() throws Exception{
		String token=UUID.randomUUID().toString();
		System.out.println(token);
			byte[] encrpted = RSAUtils.encryptByPrivateKey(token.getBytes(),privateKey);
            String sign = RSAUtils.sign(encrpted,privateKey);
            System.out.println(sign);
			BoundValueOperations operations=redisCacheUtil.setCacheObject(token, sign);
			operations.expire(exp_time, exp_unit);
		return token;
	}
	
	
	public  boolean verify(String token,byte[] encrypted) throws Exception{
		 if (token==null||encrypted==null||token.equals("")||encrypted.length==0) {
			 return false;
		 }
			String sign = (String) redisCacheUtil.getCacheObject(token);
			if (sign==null||sign.equals("")) {
				return false;
			}
//            System.out.println(RSAUtils.verify(encrypted, publicKey, sign));
            if (RSAUtils.verify(encrypted, publicKey, sign)) return true; 
		return false;
	}

	@Test
	public void getPairKey(){
		try {
			Map<String, Object> keyMap = RSAUtils.genKeyPair();
			System.out.println(RSAUtils.getPrivateKey(keyMap));
			System.out.println(RSAUtils.getPublicKey(keyMap));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void test() throws Exception {  
        System.err.println("公钥加密——私钥解密");  
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";  
        System.out.println("\r加密前文字：\r\n" + source);  
        byte[] data = source.getBytes();  
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);  
        System.out.println("加密后文字：\r\n" + new String(encodedData));  
        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);  
        String target = new String(decodedData);  
        System.out.println("解密后文字: \r\n" + target);  
        encodedData = RSAUtils.encryptByPublicKey(data, publicKey);  
        System.out.println("加密后文字：\r\n" + new String(encodedData)); 
        decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);  
        target = new String(decodedData);  
        System.out.println("解密后文字: \r\n" + target);  
        
    }  
	
	@Test
	 public void testSign() throws Exception {  
	        System.err.println("私钥加密——公钥解密");  
	        String source = "这是一行测试RSA数字签名的无意义文字";  
	        System.out.println("原文字：\r\n" + source); 
	        System.out.println(privateKey);
	        byte[] data = source.getBytes(); 
	        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);  
	        System.out.println("加密后：\r\n" + new String(encodedData));  
	        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后: \r\n" + target);  
	        System.err.println("私钥签名——公钥验证签名");  
	        String sign = RSAUtils.sign(encodedData, privateKey);  
	        System.err.println("签名:\r" + sign);  
	        boolean status = RSAUtils.verify(encodedData, publicKey, sign);  
	        System.err.println("验证结果:\r" + status);  
	    }  
	      
}
