package com.hubject.common.dto.bff;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StationDTO {

    @JSONField(name = "stationId")
    private String stationId;

    @JSONField(name = "stationName")
    private String stationName;

    @JSONField(name = "longitude")
    private String longitude;

    @JSONField(name = "latitude")
    private String latitude;

    @JSONField(name = "operatorType")
    private Integer operatorType;

    @JSONField(name = "operatorId")
    private String operatorId;

    @JSONField(name = "operatorName")
    private String operatorName;

    @JSONField(name = "address")
    private String address;

//    @JSONField(name = "pictureUrlList")
//    private List<String> pictureUrlList;
}
