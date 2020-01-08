package com.f.mml.common.qrcode;

import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/weixin/oaVoice")
public class OAVoiceController {

    @Value("${meeting.filepath}")
    private String diskPath;

    @GetMapping(value="/createOALoginQRCode")
    @ResponseBody
    public Map<String,Object> createOALoginQRCode(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();

        String optype = "login";
        String uuid = UUID.randomUUID().toString();
        String info = optype+","+uuid;
        //二维码内的信息
        String filename = uuid+".png";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        String destDirName = diskPath + "/" + ymd + "/";
        try {
            // String info = "111";
            log.info("info12{}!"+ info);
            File dirFile = new File(destDirName);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            QRCodeUtil.getQRDiskImage(info, 500, 500,destDirName,filename);
        } catch (WriterException e) {
            map.put("code","500");
            log.info("Could not generate QR Code, WriterException :: " + e.getMessage());
            return map;
        } catch (IOException e) {
            map.put("code","500");
            log.info("Could not generate QR Code, IOException :: " + e.getMessage());
            return map;
        }
        String requestUrl = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();
        String qrCodePath ="http://" + requestUrl+":"+port + contextPath + "/" + ymd + "/" + filename;
        log.info("relativename{}!"+ qrCodePath);
        map.put("data",qrCodePath);
        map.put("uuid",uuid);
        return map;
    }




}
