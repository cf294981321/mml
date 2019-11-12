package com.f.mml.extend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.mml.extend.dao.RegionMapper;
import com.f.mml.extend.domain.TAdminStrationRegion;
import com.f.mml.extend.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *   建筑物 服务实现类
 * </p>
 *
 * @author cf
 * @since 2019-09-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RegionServiceImpl extends ServiceImpl<RegionMapper, TAdminStrationRegion> implements RegionService {

    @Resource
    private RegionMapper regionMapper;


    @Override
    public List<TAdminStrationRegion> selectRegions(String regionCode) {
        List<TAdminStrationRegion> regions = new ArrayList<>();
        try{
            if(StringUtils.isNotBlank(regionCode)){
                regions = regionMapper.selectRegions(regionCode);
            }else{
                regions = regionMapper.selectProvences();
            }
            return regions;
        }catch (Exception e){
            log.error("查询区域失败！",e);
            return new ArrayList<>();
        }
    }



}

