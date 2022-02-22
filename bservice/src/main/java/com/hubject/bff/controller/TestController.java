package com.hubject.bff.controller;

import com.hubject.bff.api.TestApi;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "TestController123")
public class TestController {

    @Autowired
    private TestApi testApi;

    @ApiOperation("测试接口111")
    @ApiResponses({
            @ApiResponse(responseCode = "123",description = "惨绝人寰"),
            @ApiResponse(responseCode = "567",description = "过分任务"),
            @ApiResponse(responseCode = "000",description = "阿尚硅谷")
    })
    @GetMapping("/test")
    public String test(@RequestParam("name") String name) {
        log.info("bff call empbackend start");
        testApi.testFeign(name);
        log.info("bff call empbackend end");
        return name;
    }

}
