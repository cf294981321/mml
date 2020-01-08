package com.f.mml.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.f.mml.wx.pojo.AccessTokenVO;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AuthorizationService {

    String Oauth2API(HttpServletRequest request, Map<String,String> map);

    AccessTokenVO getAccessToken(String corpsecret);

    void messageSend(String agentid,String userid,String token,String content) throws Exception;

    JSONObject getUserVO(String code, String corpsecret);

    String getTakenCache(String code, String corpsecret);


}
