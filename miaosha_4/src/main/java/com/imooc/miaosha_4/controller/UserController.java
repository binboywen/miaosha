package com.imooc.miaosha_4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha_4.domain.MiaoshaUser;
import com.imooc.miaosha_4.redis.RedisService;
import com.imooc.miaosha_4.result.Result;
import com.imooc.miaosha_4.service.MiaoshaUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model,MiaoshaUser user) {

        return Result.success(user);
    }
    
}
