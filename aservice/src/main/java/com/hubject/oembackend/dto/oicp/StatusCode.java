package com.hubject.oembackend.dto.oicp;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class StatusCode {
    @JSONField(name = "AdditionalInfo")
    private String additionalInfo;

    @JSONField(name = "Code")
    private String code;

    @JSONField(name = "Description")
    private String description;
}
