package com.wentao.miaosha.controller;

import com.wentao.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wentao.miaosha.domain.MiaoshaOrder;
import com.wentao.miaosha.domain.MiaoshaUser;
import com.wentao.miaosha.domain.OrderInfo;
import com.wentao.miaosha.redis.RedisService;
import com.wentao.miaosha.result.CodeMsg;
import com.wentao.miaosha.result.Result;
import com.wentao.miaosha.service.GoodsService;
import com.wentao.miaosha.service.MiaoshaService;
import com.wentao.miaosha.service.MiaoshaUserService;
import com.wentao.miaosha.service.OrderService;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
	private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);
	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
	/**
	 * QPS:1306
	 * 5000 * 10
	 * */
	/**
	 *  GET POST有什么区别？
	 * */
    @RequestMapping(value="/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> miaosha(Model model,MiaoshaUser user,
    		@RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		log.info("user empty");
    		return Result.error(CodeMsg.SESSION_ERROR);    	}
    	//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		return Result.error(CodeMsg.MIAO_SHA_OVER);  	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return Result.error(CodeMsg.REPEATE_MIAOSHA);    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);    }
}
