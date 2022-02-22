package com.hubject.bff.api.hystrix;

 import com.hubject.bff.api.TestApi;
 import org.springframework.stereotype.Component;

@Component
public class TestApiHystrix implements TestApi {
    @Override
    public String testFeign(String name) {
        return null;
    }
}
