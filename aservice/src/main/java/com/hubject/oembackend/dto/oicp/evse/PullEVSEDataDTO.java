package com.hubject.oembackend.dto.oicp.evse;

import com.alibaba.fastjson.annotation.JSONField;
import com.hubject.oembackend.dto.oicp.StatusCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PullEVSEDataDTO {
    @JSONField(name = "content")
    private List<Evse> content;

    @JSONField(name = "number")
    private Integer number;

    @JSONField(name = "size")
    private Integer size;

    @JSONField(name = "totalElements")
    private Integer totalElements;

    @JSONField(name = "last")
    private Boolean last;

    @JSONField(name = "totalPages")
    private Integer totalPages;

    @JSONField(name = "first")
    private Boolean first;

    @JSONField(name = "numberOfElements")
    private Integer numberOfElements;

    @JSONField(name = "StatusCode")
    private StatusCode statusCode;

}
