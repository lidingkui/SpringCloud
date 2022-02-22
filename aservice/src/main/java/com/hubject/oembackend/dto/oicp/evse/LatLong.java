package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LatLong {
    @JSONField(name = "Coordinates")
    private String coordinates; // The string contains latitude and longitude (in this sequence) separated by a space.
}
