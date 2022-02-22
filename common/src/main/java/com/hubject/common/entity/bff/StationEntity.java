package com.hubject.common.entity.bff;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("bff_station_info")
public class StationEntity {

    private String id;

    private String stationId;

    private String stationName;

    private String longitude;

    private String latitude;

    private Integer operatorType;

    private String operatorId;

    private String operatorName;

    private String address;
}
