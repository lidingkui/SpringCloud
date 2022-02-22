package com.hubject.common.dto.bff;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StationPositionDTO {
    private double[] points;
    private String id;

    public StationPositionDTO(double[] points, String id) {
        this.points = points;
        this.id = id;
    }
}
