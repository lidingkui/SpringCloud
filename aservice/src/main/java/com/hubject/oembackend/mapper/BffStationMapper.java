package com.hubject.oembackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hubject.common.entity.bff.StationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BffStationMapper extends BaseMapper<StationEntity> {
}
