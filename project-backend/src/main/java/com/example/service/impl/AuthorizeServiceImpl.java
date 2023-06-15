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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

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
    public String sendValidateEmail(String email,String sessionId)  {
        String key="mail:"+sessionId+":"+email;
        if(Boolean.TRUE.equals(template.hasKey(key))){
            Long expire = Optional.ofNullable(template.getExpire(key)).orElse(0L);
            if(expire>120){
                return "请求频繁，请稍后再试";
            }
        }
        if(accountMapper.sendEmailValid(email)!=null){
            return "该邮箱已被注册";
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
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return "发送失败,检查地址是否正确。";
        }

    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code,String sessionId) {
        String key="mail:"+sessionId+":"+email;
        System.out.println(key);
        if(Boolean.TRUE.equals(template.hasKey(key))){
            String s=template.opsForValue().get(key);
            if(s==null){
                return "验证码已失效。";
            }
            if(s.equals(code)){
                password=encoder.encode(password);
               if(accountMapper.createAccount(username,password,email)>0){
                   template.delete(key);
                   return null;
               }else{
                   return "内部错误";
               }
            }else{
                return "验证码错误";
            }
        }else{
            return "无验证码，请先申请。";
        }
    }
}
