package com.f.mml.extend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.mml.extend.domain.TFileInfo;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author cf
 * @since 2019-09-17
 */
public interface TFileInfoService extends IService<TFileInfo> {

    TFileInfo selectFile(String fileId);

    List<TFileInfo> FileList(String fileIds);

    int updateFile(TFileInfo file, String id);

    void updateFiles(String fileIds, String id);
    void updateFiles(String[] fileIds, String id);

    /**
     *  根据 parent_id
     * @param
     * @return
     */
    List<TFileInfo> FileListByParentId(String deviceId);
}
