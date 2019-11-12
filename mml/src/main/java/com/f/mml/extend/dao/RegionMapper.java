package com.f.mml.extend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.f.mml.extend.domain.TAdminStrationRegion;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 *    Mapper 接口
 * </p>
 *
 * @author cf
 * @since 2019-10-11
 */
public interface RegionMapper extends BaseMapper<TAdminStrationRegion> {

    List<TAdminStrationRegion> selectProvences();

    List<TAdminStrationRegion> selectRegions(@Param("regionCode") String regionCode);

    List<TAdminStrationRegion> selectRegionsByCodes(@Param("codes") String codes);

}
