package com.client;

import entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(url = "http://localhost:8010",name = "user-service")
public interface UserClient {
    @RequestMapping("/user/getuser/{uid}")
    public User getUserByUid(@PathVariable("uid") int uid);

}
