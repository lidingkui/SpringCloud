package com.hubject.oembackend.dto.oicp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Sort {
    private Boolean sorted;
    private Boolean unsorted;
    private Boolean empty;
}
