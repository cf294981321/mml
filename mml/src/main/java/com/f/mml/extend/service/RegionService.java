package com.f.mml.extend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.f.mml.extend.domain.TAdminStrationRegion;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author cf
 * @since 2019-09-17
 */
public interface RegionService extends IService<TAdminStrationRegion> {


  /**
   *  根据 code 返回下级的机构。
   * @param regionCode
   * @return
   */
  List<TAdminStrationRegion> selectRegions(String regionCode);


}
