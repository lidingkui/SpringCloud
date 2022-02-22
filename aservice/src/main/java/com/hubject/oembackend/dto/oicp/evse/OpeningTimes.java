package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OpeningTimes {
    @JSONField(name = "on")
    private String on;
    @JSONField(name = "Period")
    private List<Period> period;
}
