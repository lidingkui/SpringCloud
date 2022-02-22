package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ChargingFacilities {
    @JSONField(name = "Amperage")
    private Integer amperage;
    @JSONField(name = "Power")
    private Integer power;
    @JSONField(name = "PowerType")
    private String powerType;
    @JSONField(name = "Voltage")
    private Integer voltage;
    @JSONField(name = "ChargingModes")
    private List<String> chargingModes;
}
