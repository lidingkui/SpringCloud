package com.hubject.bff.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "oembackend")
public interface TestApi {

    @GetMapping("/test")
    String testFeign(@RequestParam("name") String name);
}
