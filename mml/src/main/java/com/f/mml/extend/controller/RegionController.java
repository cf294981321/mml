package com.f.mml.extend.controller;

import cn.hutool.http.HttpStatus;
import com.f.mml.common.utils.Response;
import com.f.mml.extend.domain.TAdminStrationRegion;
import com.f.mml.extend.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("sd/region")
public class RegionController {

    @Resource
    private RegionService regionService;

    private String message;

    private static Logger logger = LoggerFactory.getLogger(RegionController.class);

    @GetMapping("/selectRegions")
    public Response selectRegions(String regionCode) {
        List<TAdminStrationRegion> list = regionService.selectRegions(regionCode);
        return new Response().code(HttpStatus.HTTP_OK).message("操作成功").data(list);
    }




}
