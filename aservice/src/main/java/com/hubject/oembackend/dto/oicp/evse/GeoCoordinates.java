package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GeoCoordinates {
    @JSONField(name = "Google")
    private LatLong google;

    @JSONField(name = "DecimalDegree")
    private LatLong decimalDegree;

    @JSONField(name = "DegreeMinuteSeconds")
    private LatLong degreeMinuteSeconds;
}
