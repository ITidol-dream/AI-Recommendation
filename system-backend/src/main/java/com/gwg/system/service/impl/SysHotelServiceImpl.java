package com.shanzhu.tourism.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.shanzhu.tourism.domain.SysAttractions;
import com.shanzhu.tourism.domain.SysCommentsHotels;
import com.shanzhu.tourism.domain.SysHotel;
import com.shanzhu.tourism.mapper.SysHotelMapper;
import com.shanzhu.tourism.mapper.SysHotelsCommentsMapper;
import com.shanzhu.tourism.service.SysHotelService;
import com.shanzhu.tourism.utils.recommend.CoreMath;
import com.shanzhu.tourism.utils.recommend.dto.ProductDTO;
import com.shanzhu.tourism.utils.recommend.dto.RelateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysHotelServiceImpl extends ServiceImpl<SysHotelMapper, SysHotel> implements SysHotelService {

    @Value("${useRecommend}")
    private Boolean userRecommend;

    @Resource
    private SysHotelsCommentsMapper  sysHotelsCommentsMapper;

    @Resource
    private SysHotelMapper sysHotelMapper;
    @Override
    public List<SysHotel> searchHotels(String userId) {
        //判断是否需要走推荐算法
        if(BooleanUtil.isFalse(userRecommend)) {
            QueryWrapper<SysHotel> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysHotel::getState, 1).last("limit 3");
            return this.list(queryWrapper);
        }
        return recommendHotels(userId);
    }
    private List<SysHotel> recommendHotels(String userId){
        CoreMath coreMath = new CoreMath();
        List<RelateDTO> relateDTOList = generateRelates();
        //执行推荐算法
        List<String> recommendations = coreMath.recommend(userId, relateDTOList);

        List<SysHotel> sysHotels = new ArrayList<>();

        for (String productId : recommendations) {
            sysHotels.add(sysHotelMapper.selectById(productId));
        }
        System.out.println("sysHotels"+sysHotels);
        Collections.reverse(sysHotels);


        int size = Math.min(3, sysHotels.size());
        List<SysHotel> top3Hotels = sysHotels.subList(0, size);
        return top3Hotels;
    }

    /**
     * 获取相关的所有评论数据
     *
     * @return 相关评论数据
     */
    private List<RelateDTO> generateRelates() {
        //查询所有商品的评论
        List<SysCommentsHotels> comments = sysHotelsCommentsMapper.selectList(Wrappers.emptyWrapper());
        List<RelateDTO> relateDTOList = Lists.newArrayList();
        for (SysCommentsHotels sysCommentsHotels : comments) {
            RelateDTO relateDTO = new RelateDTO();
            relateDTO.setUserId(sysCommentsHotels.getUserId());
            relateDTO.setProductId(sysCommentsHotels.getHotelsId());
            relateDTO.setIndex(sysCommentsHotels.getScore());
            relateDTOList.add(relateDTO);
        }
        if (CollectionUtils.isEmpty(relateDTOList)) {
            log.info("--------------------List<RelateDTO>为空！");
            System.out.println("--------------------List<RelateDTO>为空！");
        }

        return relateDTOList;
    }

    /**
     * 获取相关的所有业务数据
     *
     * @return 相关数据
     */
    private List<ProductDTO> generateProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<SysHotel> sysHotels = sysHotelMapper.selectList(Wrappers.emptyWrapper());
        for (SysHotel hotel : sysHotels) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(hotel.getId());
            productDTOList.add(productDTO);
        }
        if (CollectionUtils.isEmpty(productDTOList)) {
            log.info("----------------------List<ProductDTO>为空！");
            System.out.println("----------------------List<ProductDTO>为空！");
        }
        return productDTOList;
    }
}
