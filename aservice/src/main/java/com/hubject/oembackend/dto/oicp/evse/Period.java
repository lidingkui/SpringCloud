package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Period {
    @JSONField(name = "begin")
    private String begin;
    @JSONField(name = "end")
    private String end;
}
