package com.hubject.oembackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hubject.common.constant.redis.RedisKey;
import com.hubject.common.dto.bff.StationPositionDTO;
import com.hubject.common.entity.bff.StationEntity;
import com.hubject.common.util.KDTree;
import com.hubject.common.util.PositionUtils;
import com.hubject.oembackend.mapper.BffStationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InnerServiceImpl {

    @Autowired
    private BffStationMapper bffStationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public void buildPOISpaceModel() {
        List<StationEntity> all = bffStationMapper.selectList(new QueryWrapper<>());
        List<StationPositionDTO> list = new ArrayList<>();
        for (StationEntity entity : all) {
            StationPositionDTO dto = new StationPositionDTO(PositionUtils.LatLong2Vector3(Double.parseDouble(entity.getLatitude()), Double.parseDouble(entity.getLongitude())), entity.getId());
            list.add(dto);
        }
        KDTree kdTree = new KDTree(3, list);
        redisTemplate.opsForValue().set(RedisKey.STATIONS_TREE, KDTree.serialize(kdTree.getK(),kdTree.getRoot()));
    }
}
