package com.walter.user.api.feign;

import org.springframework.cloud.openfeign.FeignClient;

import com.walter.user.api.UserApi;

@FeignClient(name="user", path="/api/user")
public interface UserFeignApi extends UserApi {
}
