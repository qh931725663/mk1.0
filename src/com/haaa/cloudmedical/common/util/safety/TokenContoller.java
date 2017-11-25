package com.haaa.cloudmedical.common.util.safety;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haaa.cloudmedical.common.util.InfoJson;

@RestController
@RequestMapping("/token")
public class TokenContoller {

	@Resource
	private TokenUtils tokenUtils;

 @RequestMapping("/verification.action")
 public InfoJson tokenVerify(String token) throws Exception{
//	 token = "11c28e64-0319-4bac-af3e-e6c98a69f7e5";
	 byte[] encrypted = RSAUtils.encryptByPrivateKey(token.getBytes(), tokenUtils.privateKey);
	 InfoJson infoJson = new InfoJson();
	 if (token==null||encrypted==null||token.equals("")||encrypted.length==0) {
		 infoJson.setStatus(1);
	}
	 if(tokenUtils.verify(token, encrypted)){
		 infoJson.setStatus(0);
	 }else{
		 infoJson.setStatus(1);
		 infoJson.setData(tokenUtils.tokenGenerator());
	 }
	 return infoJson;
 }
}