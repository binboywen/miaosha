package com.imooc.miaosha_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.imooc.miaosha_2.domain.MiaoshaUser;
import com.imooc.miaosha_2.redis.RedisService;
import com.imooc.miaosha_2.service.MiaoshaUserService;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model,MiaoshaUser user) {
    	if(user == null)
    		return "login";
    	model.addAttribute("user", user);
        return "goods_list";
    }
    
}
