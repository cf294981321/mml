package com.f.mml.extend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.mml.extend.domain.vo.MeetingVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface TableMapper extends BaseMapper<MeetingVO> {

    List<String> getTableNames();

    List<String> getColumnNames(@Param("tname") String tname);

    Integer queryValueForColumn(@Param("tname") String tname, @Param("cname") String cname);

    Integer insertResult(@Param("tname") String tname, @Param("cname") String cname);

}
