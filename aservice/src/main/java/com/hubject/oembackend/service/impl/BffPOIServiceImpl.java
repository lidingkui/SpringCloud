package com.hubject.oembackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hubject.common.entity.bff.StationEntity;
import com.hubject.oembackend.converter.bff.EvseData2BFFStation;
import com.hubject.oembackend.dto.oicp.evse.Evse;
import com.hubject.oembackend.dto.oicp.evse.PullEVSEDataDTO;
import com.hubject.oembackend.mapper.BffStationMapper;
import com.hubject.oembackend.service.POIService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BffPOIServiceImpl implements POIService {
    @Autowired
    private BffStationMapper bffStationMapper;

    @Override
    public void sync(PullEVSEDataDTO evseDataDTO) {
        List<StationEntity> stations = new ArrayList<>();
        Set<String> vis = new HashSet<>();

        List<Evse> evseList = evseDataDTO.getContent();
        // stations
        for (Evse evse : evseList) {
            // 本地去重 operatorId + stationId
            String s = evse.getOperatorID() + " " + evse.getChargingStationID();
            if (StringUtils.isBlank(evse.getChargingStationID()) || vis.contains(s)) continue;
            StationEntity stationEntity = new StationEntity();
            BeanUtils.copyProperties(EvseData2BFFStation.cvt(evse), stationEntity);
            stations.add(stationEntity);
            vis.add(s);
        }
        syncStation2DB(stations);


        // todo evse & connector

    }

//    class DisjointSet<T> {
//        int[] parent;
//        HashMap<String, Integer> pos;
//
//        public DisjointSet(List<StationEntity> list) {
//            this.parent = new int[list.size()];
//            for (int i = 0; i < parent.length; i++) {
//                StationEntity stationEntity = list.get(i);
//                stationEntity.getOperatorId()
//                stationEntity.getStationId()
//                parent[i] = i;
//            }
//        }
//    }

    private void syncStation2DB(List<StationEntity> stations) {

        Set<String> operatorIds = stations.stream().map(StationEntity::getOperatorId).collect(Collectors.toSet());
        List<StationEntity> stationEntityList = operatorIds.isEmpty() ? new ArrayList<>() :
                bffStationMapper.selectList(new QueryWrapper<StationEntity>().lambda().in(StationEntity::getOperatorId, operatorIds));

        Map<String, StationEntity> vis = new HashMap<>();
        for (StationEntity stationEntity : stationEntityList) {
            vis.put(stationEntity.getOperatorId() + " " + stationEntity.getStationId(), stationEntity);
        }
        List<StationEntity> addList = new ArrayList<>();
        List<StationEntity> updateList = new ArrayList<>();

        for (StationEntity station : stations) {
            String key = station.getOperatorId() + " " + station.getStationId();
            if (vis.containsKey(key)) {
                String id = vis.get(key).getId();
                BeanUtils.copyProperties(station, vis.get(key));
                vis.get(key).setId(id);
                updateList.add(vis.get(key));
            } else {
                station.setId(UUID.randomUUID().toString());
                addList.add(station);
            }
        }

        for (StationEntity stationEntity : updateList) {
            bffStationMapper.updateById(stationEntity);
        }

        for (StationEntity stationEntity : addList) {
            bffStationMapper.insert(stationEntity); // todo 批量操作
        }
    }

    private void syncEvse2DB() {

    }

    private void syncConnector2DB() {

    }
}
