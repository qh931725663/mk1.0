package com.haaa.cloudmedical.wechat.service;

import java.io.IOException;
import java.util.Map;

public interface IWeixinService {

    String handlerMsg(Map<String, String> msgMap) throws IOException;

}
