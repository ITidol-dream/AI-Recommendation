package com.shanzhu.tourism.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanzhu.tourism.domain.SysHotel;
import java.util.List;

public interface SysHotelService extends IService<SysHotel> {

    List<SysHotel> searchHotels(String userId);

}
