package com.controller;

import com.service.UserService;
import entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    UserService userService;
    @RequestMapping("/user/getuser/{uid}")
    public User getUserByUid(@PathVariable("uid") int uid){
        return userService.getUserByUid(uid);
    }
}
