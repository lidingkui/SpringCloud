package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EnergySource {
    @JSONField(name = "Energy")
    private String energy;
    @JSONField(name = "Percentage")
    private Integer percentage;
}
