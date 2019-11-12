package com.f.mml.extend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.mml.extend.domain.TFileInfo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface TFileInfoMapper extends BaseMapper<TFileInfo> {

    public Integer updateFile(@Param("fileId") String fileId, @Param("id") String id);

    List<TFileInfo> fileListByParentId(@Param("deviceId") String deviceId);

}
