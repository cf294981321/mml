package com.f.mml.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f.mml.wx.pojo.AccessTokenVO;
import com.f.mml.wx.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    @Value("${weixin.application.corpid}")
    private String corpid;
    @Resource
    private RestTemplate restTemplate;


    @Override
    public String Oauth2API(HttpServletRequest request, Map<String,String> map) {
        log.info("start Oauth2API param is second");
        String url = map.get("url");
        //获取项目域名
        String requestUrl = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        log.info("domain name: " + requestUrl + " project name: " + contextPath);
        //拼接微信回调地址
        String backUrl ="http://" + requestUrl+":"+port + contextPath + url;
        // String backUrl ="http://oa.yiyuncampus.com:52324/oaextend/weixin/authorization/"+url;

        log.info("backUrl is:{}"+backUrl);
        String redirect_uri = "";
        try {
            redirect_uri = java.net.URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("ecdoe error: " + e.getMessage());
        }
        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + corpid + "&redirect_uri=" + redirect_uri
                + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

        if (url.contains("oauth2me")){
            log.info("进入认证个人信息");
            return "redirect:" + oauth2Url;
        }
        if(map.get("redirect") != null){
            return "redirect:" + oauth2Url;
        }
        return oauth2Url;
    }

    @Override
    public JSONObject getUserVO(String code,String corpsecret) {
        log.info("start method getUserVO");
        log.info("code is:{}",code);
        //从缓存中获取
        net.sf.ehcache.CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("auth2");
        String token = "";
        if( cache != null){
            token = cache.get(corpsecret)==null ? "":cache.get(corpsecret).getObjectValue().toString();
            log.info("token is:{}",token);
            if (StringUtils.isEmpty(token)){
                AccessTokenVO accessTokenVO = getAccessToken(corpsecret);
                token = accessTokenVO.getAccess_token();
                log.info("first get token:{}"+token);
            }
        }
        //获取不到,调用接口
        //https://qyapi.weixin.qq.com/cgi-bin/service/getuserinfo3rd
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+token+"&code="+code;
        //ResponseEntity<UserVO> responseEntity = restTemplate.getForEntity(url, UserVO.class);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        int statusCode = responseEntity.getStatusCodeValue();
        log.info("statusCode:{}",statusCode);
        if (!(statusCode == 200)){
            log.info("cache is lapse,reacquire token");
            //token失效
            //清除缓存
            cache.remove(corpsecret);
            AccessTokenVO accessTokenVO = getAccessToken(corpsecret);
            log.info("new token is:{}",accessTokenVO.getAccess_token());
            String url2 = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+accessTokenVO.getAccess_token()+"&code="+code;
            responseEntity = restTemplate.getForEntity(url2, String.class);
            log.info("new body:{}",responseEntity.getBody());
            //log.info("errorCode:{},userId:{}",responseEntity.getBody().getErrcode(),responseEntity.getBody().getUserId());
        }
        log.info("body:{}",responseEntity.getBody());
        log.info("end method getUserVO");
        String  userInfo = responseEntity.getBody();
        //解析数据
        JSONObject jsonObject = JSON.parseObject(userInfo);
        String userId = jsonObject.getString("UserId");
        String errCode = jsonObject.getString("errcode");
        String msg = jsonObject.getString("errmsg");
        String deviceId = jsonObject.getString("DeviceId");
        //表示成功
        if ("0".equals(errCode)){
            log.info("code:{}",errCode);
            log.info("userId:{}",userId);
            return jsonObject;
        }else{
            log.info("code:{}",errCode);
        }
        return null;
    }

    @Override
    public AccessTokenVO getAccessToken(String corpsecret) {
        log.info("start method getAccessToken");
        ResponseEntity<AccessTokenVO> responseEntity = new ResponseEntity<AccessTokenVO>(HttpStatus.OK);
        AccessTokenVO accessTokenVO = new AccessTokenVO();
        //从缓存中获取,设置过期时间
        net.sf.ehcache.CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("auth2");
        //String token2 = cache.get(corpsecret).getObjectValue().toString();
        if (cache.get(corpsecret) == null || StringUtils.isEmpty(cache.get(corpsecret).getObjectValue().toString())){
            log.info("ehecahe put accesstoken");
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
            responseEntity = restTemplate.getForEntity(url,AccessTokenVO.class);
            Element element = new Element(corpsecret,responseEntity.getBody().getAccess_token());
            cache.put(element);
        }else{
            log.info("ehecahe get accesstoken");
            String token = cache.get(corpsecret).getObjectValue().toString();
            accessTokenVO.setErrcode("0");
            accessTokenVO.setAccess_token(token);
            accessTokenVO.setErrmsg("ok");
            return accessTokenVO;
        }
        log.info("end method getAccessToken");
        return responseEntity.getBody();
    }


    @Override
    public String getTakenCache(String code,String corpsecret) {
        log.info("start getTakenCache");
        log.info("code is:{}",code);
        //从缓存中获取
        net.sf.ehcache.CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("auth2");
        String token = cache.get(corpsecret)==null ? "":cache.get(corpsecret).getObjectValue().toString();
        log.info("token is:{}",token);
        if (StringUtils.isEmpty(token)){
            AccessTokenVO accessTokenVO = getAccessToken(corpsecret);
            token = accessTokenVO.getAccess_token();
            log.info("first get token:{}"+token);
        }
        return token;
    }

    @Override
    public void messageSend(String agentid,String userid,String token,String content) throws Exception{
        Map map = new HashMap();
        map.put("touser",userid);
        map.put("msgtype","text");
        map.put("agentid",agentid);
        JSONObject obj = new JSONObject();
        obj.put("content",content);
        map.put("text",obj);
        map.put("safe",0);
        map.put("enable_id_trans",0);
        map.put("enable_duplicate_check",0);

        String path = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token;

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=utf-8");
        headers.setContentType(type);
        headers.set("Content-Encoding ","gzip");
        headers.set("Server","Microsoft-HTTPAPI/2.0");
        headers.set("Transfer-Encoding","chunked");
        headers.set("Vary","Accept-Encoding");
        headers.set("X-Content-Type-Options","nosniff");
        headers.set("X-Frame-Options","deny");
        headers.add("Authorization", "bearer "+token);

        String jsonString = JSONObject.toJSONString(map);
        HttpEntity<String> httpEntity = new HttpEntity<String>(jsonString, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(path, httpEntity,String.class);

    }


}
