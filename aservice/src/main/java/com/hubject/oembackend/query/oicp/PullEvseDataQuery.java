package com.hubject.oembackend.query.oicp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class PullEvseDataQuery {
    @JSONField(name = "ProviderID")
    private String providerID;

    @JSONField(name = "GeoCoordinatesResponseFormat")
    private String geoCoordinatesResponseFormat;

    @JSONField(name = "LastCall")
    private String lastCall;

    @JSONField(name = "OperatorIds")
    private List<String> operatorIds;

}