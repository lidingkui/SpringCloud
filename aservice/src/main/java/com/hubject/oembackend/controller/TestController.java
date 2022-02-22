package com.hubject.oembackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hubject.common.constant.redis.RedisKey;
import com.hubject.common.util.KDTree;
import com.hubject.common.util.MdcThreadTaskUtils;
import com.hubject.common.util.PositionUtils;
import com.hubject.oembackend.dto.oicp.evse.PullEVSEDataDTO;
import com.hubject.oembackend.service.impl.InnerServiceImpl;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@Slf4j
@ApiModel(description = "TestController",value = "TestControllerVal")
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private InnerServiceImpl innerService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiModelProperty(value = "test",example = "{'id':'xxx'}")
    @GetMapping("/test")
    public String testFeign(@RequestParam("name") String name) {
        log.info("oembackend param: {}", name);
        MdcThreadTaskUtils.execute(() -> log.info("oembackend thread execute"));
        return name;
    }

    @GetMapping("/test1")
    public String testRestTemplate() {
        ResponseEntity<String> ret = restTemplate.getForEntity("https://www.baidu.com", String.class);
        return ret.getBody();
    }

    @GetMapping("/test2")
    public Object testPullEvseData() {
        // https://service-qa.hubject.com/api/oicp/evsepull/v23/providers/{providerID}/data-records
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("LU*CHY");
        jsonObject.put("ProviderID", "CN-MME");
        jsonObject.put("GeoCoordinatesResponseFormat", "Google");
        jsonObject.put("OperatorIds", strings);
        ResponseEntity<PullEVSEDataDTO> ret = restTemplate.postForEntity("http://172.31.22.50:8073/api/oicp/evsepull/v23/providers/CN-MME/data-records", jsonObject, PullEVSEDataDTO.class);
        return ret.getBody();
    }

    @GetMapping("/test3")
    public String test3() {
        innerService.buildPOISpaceModel();
        return "sync 2 redis";
    }

    @GetMapping("/test4")
    public String test4(@RequestParam("topRightLon") double topRightLon, @RequestParam("topRightLat") double topRightLat
            , @RequestParam("bottomLeftLon") double bottomLeftLon, @RequestParam("bottomLeftLat") double bottomLeftLat) {
        double[] a = PositionUtils.LatLong2Vector3(bottomLeftLat, topRightLon);
        double[] b = PositionUtils.LatLong2Vector3(bottomLeftLat, bottomLeftLon);
        double[] c = PositionUtils.LatLong2Vector3(topRightLat, bottomLeftLon);
        double[] d = PositionUtils.LatLong2Vector3(topRightLat, topRightLon);

        double[] low = new double[3];
        double[] height = new double[3];

        for (int i = 0; i < 3; i++) {
            double[] val = getMinAndMaxValue(a[i], b[i], c[i], d[i]);
            low[i] = val[0];
            height[i] = val[1];
        }
        String str = (String)redisTemplate.opsForValue().get(RedisKey.STATIONS_TREE);
        if (StringUtils.isBlank(str)) throw new RuntimeException("序列化异常");
        KDTree tree = KDTree.deserialize(str);
        if (tree == null ) return "empty....";
        return tree.rangePoints(low,height).toString();
    }

    @GetMapping("/test5")
    public String test5(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {
        String str = (String)redisTemplate.opsForValue().get(RedisKey.STATIONS_TREE);
        if (StringUtils.isBlank(str)) throw new RuntimeException("序列化异常");
        KDTree tree = KDTree.deserialize(str);
        if (tree == null ) return "empty....";
        return tree.nearestPoint(PositionUtils.LatLong2Vector3(lat, lng),1).get(0);
    }


    private double[] getMinAndMaxValue(double... nums) {
        double min = nums[0];
        double max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (min > nums[i]) min = nums[i];
            if (max < nums[i]) max = nums[i];
        }
        return new double[]{min, max};
    }
}
