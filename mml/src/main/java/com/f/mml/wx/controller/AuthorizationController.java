package com.f.mml.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.f.mml.common.id.SnowflakeIdWorker;
import com.f.mml.common.qrcode.QRCodeUtil;
import com.f.mml.wx.service.AuthorizationService;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 登录认证
 */
@Controller
@Slf4j
@RequestMapping("/weixin/authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @Value("${meeting.file-path}")
    private String diskPath;
    @Value("${weixin.application.agentid}")
    private String agentid;
    @Value("${weixin.application.corpsecret}")
    private String corpsecret;

    @GetMapping(value="/createQRCode")
    @ResponseBody
    public Map<String,Object> createQRCode(HttpServletRequest request, Integer meetingid) {
        Map<String,Object> result = new HashMap<>();

        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        Long uuid = idWorker.nextId();

        //二维码内的信息
        Map<String,String> map = new HashMap<>();
        map.put("url","/weixin/authorization/scanCodePage?meetingid="+meetingid+"&uuid="+uuid);
        //String info = "http://oa.yiyuncampus.com:52324/oaextend/weixin/authorization/oauth2wx";

        String filename = uuid+".png";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        String destDirName = diskPath + "/" + ymd + "/";
        try {
           String info = authorizationService.Oauth2API(request,map);
           // String info = "111";
            log.info("info12{}!"+ info);
            File dirFile = new File(destDirName);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            QRCodeUtil.getQRDiskImage(info, 500, 500,destDirName,filename);
        } catch (WriterException e) {
            result.put("code","500");
            log.info("Could not generate QR Code, WriterException :: " + e.getMessage());
            return result;
        } catch (IOException e) {
            result.put("code","500");
            log.info("Could not generate QR Code, IOException :: " + e.getMessage());
            return result;
        }
        String requestUrl = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        String qrCodePath ="http://" + requestUrl+":"+port + contextPath + "/" + ymd + "/" + filename;
        log.info("relativename{}!"+ qrCodePath);
        result.put("data",qrCodePath);
        result.put("uuid",uuid);
        return result;
    }

    /**
     * 扫码进入会议
     * @param code
     */
    @GetMapping("/scanCodePage")
    public String scanCodePage(HttpServletRequest request, @RequestParam String code, @RequestParam String meetingid, @RequestParam String uuid){
       // String res = "扫码成功!";
        try {
            //获取用户信息
            JSONObject jsonObject = authorizationService.getUserVO(code,corpsecret);
            String userId = jsonObject.getString("UserId");

            if(StringUtils.isNotBlank(userId) ){

                try {
                    String token = authorizationService.getTakenCache(code,corpsecret);
                    authorizationService.messageSend(agentid,userId,token,"欢迎，请入座");
                    net.sf.ehcache.CacheManager cacheManager = CacheManager.getInstance();
                    Cache cache = cacheManager.getCache("QRCode");
                    if (cache.get(uuid) == null || org.springframework.util.StringUtils.isEmpty(cache.get(uuid).getObjectValue().toString())){
                        Map<String,String> map = new HashMap<>();
                        map.put(uuid,userId);
                        Element element = new Element(uuid,map);
                        cache.put(element);
                    }

                } catch (Exception e) {
                }
            }
            //res = "扫码成功! 返回：https://work.weixin.qq.com/wework_admin/frame#contacts";
        } catch (Exception e) {
            log.error("扫码失败!");
            log.info(e.getMessage());
            return  "redirect:" +"http://oa.yiyuncampus.com:52324/oaextend/welcomefail.html";
        }
        return  "redirect:" +"http://oa.yiyuncampus.com:52324/oaextend/welcome.html";
    }


}
