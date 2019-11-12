package com.f.mml.extend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.f.mml.extend.dao.TFileInfoMapper;
import com.f.mml.extend.service.TFileInfoService;
import com.f.mml.extend.domain.TFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *    服务实现类
 * </p>
 *
 * @author cf
 * @since 2019-09-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TFileInfoServiceImpl extends ServiceImpl<TFileInfoMapper, TFileInfo> implements TFileInfoService {

    @Resource
    private TFileInfoMapper tFileInfoMapper;


    @Override
    public TFileInfo selectFile(String fileId) {
        return tFileInfoMapper.selectById(fileId);
    }

    @Override
    public List<TFileInfo> FileList(String fileIds) {
        List<TFileInfo> list =  new ArrayList<>();
        if(StringUtils.isNotBlank(fileIds)){
            String[] arr = fileIds.split(",");
            for(int i=0;i <arr.length;i++){
                if(StringUtils.isNotBlank( arr[i] )){
                    TFileInfo fileInfo = tFileInfoMapper.selectById(arr[i]);
                    if(fileInfo != null){
                        list.add(fileInfo);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int updateFile(TFileInfo file,String id) {
        return tFileInfoMapper.updateFile(file.getFileId().toString(),id);
    }

    @Override
    public void updateFiles(String fileIds,String id) {
        if(StringUtils.isNotBlank(fileIds)){
            String[] arr = fileIds.split(",");
            for(int i=0;i <arr.length;i++){
                tFileInfoMapper.updateFile(arr[i],id);
            }
        }
    }
    @Override
    public void updateFiles(String[] fileIds,String id) {
        if(fileIds != null){
            for(int i=0;i <fileIds.length;i++){
                tFileInfoMapper.updateFile(fileIds[i],id);
            }
        }
    }
    @Override
    public List<TFileInfo> FileListByParentId(String parentId) {
        List<TFileInfo> list = tFileInfoMapper.fileListByParentId(parentId);
        return list;
    }

}

