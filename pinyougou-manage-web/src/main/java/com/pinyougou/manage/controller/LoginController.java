package com.pinyougou.manage.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    /**
     * 获取当前登录的用户名
     * @return
     */
    @GetMapping("getUsername")
    public Map<String,Object> getUsername(){
        Map<String,Object> result=new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        result.put("username",username);
        return result;
    }
}