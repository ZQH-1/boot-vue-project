package com.example.controller;

import com.example.entity.RestBean;
import com.example.service.AuthorizeService;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthorizationController {

    private final String EMAIL_REGEXP="^[a-zA-Z0-9.%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    private final String USERNAME_REGEXP="^[a-zA-Z0-9\u4e00-\u9fa5]+$";
    @Resource
    AuthorizeService authorizeService;

    @PostMapping("/valid-email")
    public RestBean<String> validateEmail(@Pattern (regexp = EMAIL_REGEXP)@RequestParam("email")String email, HttpSession session){
        String s=authorizeService.sendValidateEmail(email,session.getId());
        if(s==null){
           return RestBean.success("邮件已发送!请注意查收");
        }else{
           return RestBean.failure(400,s);
        }
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USERNAME_REGEXP) @Length(min = 3,max = 8)@RequestParam("username") String username,
                                         @Length(min = 6,max = 16) @RequestParam("password") String password,
                                         @Pattern(regexp = EMAIL_REGEXP) @RequestParam("email") String email,
                                         @Length(min = 6,max = 6)@RequestParam("code") String code,
                                         HttpSession session){
        String s=authorizeService.validateAndRegister(username, password, email, code,session.getId());
        if(s==null){
            return RestBean.success("注册成功");
        }
        return RestBean.failure(400,s);
    }
}
