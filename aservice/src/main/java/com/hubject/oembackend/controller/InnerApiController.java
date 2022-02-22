package com.hubject.oembackend.controller;

import com.alibaba.fastjson.JSON;
import com.hubject.common.util.MdcThreadTaskUtils;
import com.hubject.oembackend.dto.oicp.evse.PullEVSEDataDTO;
import com.hubject.oembackend.query.oicp.PullEvseDataQuery;
import com.hubject.oembackend.service.POIService;
import com.hubject.oembackend.service.impl.InnerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
@RequestMapping("/inner")
public class InnerApiController {

    @Autowired
    private POIService poiService;

    @Autowired
    private InnerServiceImpl innerService;

    @Autowired
    private RestTemplate restTemplate;

    // TODO
    @GetMapping("/poi/sync")
    public String SyncPOI() {

        PullEvseDataQuery pullEvseDataQuery = new PullEvseDataQuery();
        pullEvseDataQuery.setProviderID("CN-MME");
        pullEvseDataQuery.setGeoCoordinatesResponseFormat("Google");

        //?page={}&size={}
        MdcThreadTaskUtils.execute(() -> {
            String url = "http://172.31.22.50:8073/api/oicp/evsepull/v23/providers/CN-MME/data-records?page=%s&size=1000";
            int page = 0;
            int totalPage = 0;
            while (page <= totalPage) {
                ResponseEntity<PullEVSEDataDTO> ret
                        = restTemplate.postForEntity(String.format(url, page), JSON.toJSON(pullEvseDataQuery), PullEVSEDataDTO.class);
                poiService.sync(ret.getBody());
                if (ret.getBody() == null) break;
                totalPage = ret.getBody().getTotalPages();
                page++;
            }
            // 在三维空间中构建站点位置信息
            innerService.buildPOISpaceModel();
        });
        return "execute......";
    }


}
