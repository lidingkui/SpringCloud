package com.hubject.oembackend.service;

import com.hubject.oembackend.dto.oicp.evse.PullEVSEDataDTO;

public interface POIService {

    void sync(PullEVSEDataDTO evseDataDTO);
}
