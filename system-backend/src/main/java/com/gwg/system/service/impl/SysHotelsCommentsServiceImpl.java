package com.shanzhu.tourism.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanzhu.tourism.domain.SysCommentsHotels;
import com.shanzhu.tourism.mapper.SysHotelsCommentsMapper;
import com.shanzhu.tourism.service.SysHotelsCommentsService;
import org.springframework.stereotype.Service;

@Service
public class SysHotelsCommentsServiceImpl extends ServiceImpl<SysHotelsCommentsMapper, SysCommentsHotels> implements SysHotelsCommentsService {
}
