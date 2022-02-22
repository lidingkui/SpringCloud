package com.hubject.oembackend.dto.oicp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Pageable {
    private Sort sort;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer offset;
    private Boolean paged;
    private Boolean unpaged;
}
