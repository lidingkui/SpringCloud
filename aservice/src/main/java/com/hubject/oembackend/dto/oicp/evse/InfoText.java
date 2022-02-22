package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class InfoText {
    @JSONField(name = "lang")
    private String lang;
    @JSONField(name = "value")
    private String value;
}
