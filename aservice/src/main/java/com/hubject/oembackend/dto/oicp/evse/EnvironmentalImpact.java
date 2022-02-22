package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnvironmentalImpact {
    @JSONField(name = "CO2Emission")
    private Double CO2Emission;

    @JSONField(name = "NuclearWaste")
    private Double nuclearWaste;
}
