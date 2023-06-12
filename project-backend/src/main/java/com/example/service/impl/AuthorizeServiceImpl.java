package com.example.service.impl;

import com.example.entity.Account;
import com.example.mapper.AccountMapper;
import com.example.service.AuthorizeService;
import org.apache.ibatis.javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    @Resource
    AccountMapper accountMapper;
    @Resource
    MailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Resource
    StringRedisTemplate template;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null){
            throw new UsernameNotFoundException("用户名不能为空");
        }
        Account account = accountMapper.loginByUsername(username);
        if(account==null){
            throw new UsernameNotFoundException("用户名/密码错误");
        }
        return User.withUsername(account.getUsername())
                    .password(account.getPassword())
                    .roles("user")
                    .build();
    }

    @Override
    public boolean sendValidateEmail(String email,String sessionId)  {
        String key="mail:"+sessionId+":"+email;
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key)).orElse(0L);
            if(expire>120){
                return false;
            }
        }

        System.out.println(email);
        Random random=new Random();
        int code=random.nextInt(899999)+100000;

        SimpleMailMessage mail=new SimpleMailMessage();
        mail.setFrom(fromAddress);
        mail.setText("您的验证码为："+code);
        mail.setTo(email);
        mail.setSubject("验证码");
        try {
            mailSender.send(mail);
            System.out.println("发送成功");
            template.opsForValue().set(key,String.valueOf(code),3,TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
