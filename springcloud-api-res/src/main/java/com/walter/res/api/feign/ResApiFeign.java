package com.walter.res.api.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.walter.res.api.ResApi;

@FeignClient(name="res", path="/api/res")
public interface ResApiFeign extends ResApi {
}
