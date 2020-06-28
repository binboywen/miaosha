package com.imooc.miaosha_2.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.imooc.miaosha_2.dao.MiaoshaUserDao;
import com.imooc.miaosha_2.domain.MiaoshaUser;
import com.imooc.miaosha_2.exception.GlobalException;
import com.imooc.miaosha_2.redis.MiaoshaUserKey;
import com.imooc.miaosha_2.redis.RedisService;
import com.imooc.miaosha_2.result.CodeMsg;
import com.imooc.miaosha_2.util.MD5Util;
import com.imooc.miaosha_2.util.UUIDUtil;
import com.imooc.miaosha_2.vo.LoginVo;
@Component
@Service
public class MiaoshaUserService {
	
	
	public static final String COOKI_NAME_TOKEN = "token";
	
	@Autowired
	MiaoshaUserDao miaoshaUserDao;
	
	@Autowired
	RedisService redisService;
	
	public MiaoshaUser getById(long id) {
		return miaoshaUserDao.getById(id);
	}
	

	public MiaoshaUser getByToken(HttpServletResponse response, String token) {
		if(StringUtils.isEmpty(token)) {
			return null;
		}
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		//延长有效期
		if(user != null) {
			addCookie(response, token, user);
		}
		return user;
	}
	

	public boolean login(HttpServletResponse response, LoginVo loginVo) {
		if(loginVo == null) {
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formPass = loginVo.getPassword();
		//判断手机号是否存在
		MiaoshaUser user = getById(Long.parseLong(mobile));
		if(user == null) {
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		//验证密码
		String dbPass = user.getPassword();
		String saltDB = user.getSalt();
		String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
		if(!calcPass.equals(dbPass)) {
			throw new GlobalException(CodeMsg.PASSWORD_ERROR);
		}
		//生成cookie
		String token = UUIDUtil.uuid();
		//redisService.set(MiaoshaUserKey.token,token,user);
		//Cookie cookie = new Cookie(COOKI_NAME_TOKEN,token);
		addCookie(response, token, user);
		return true;
	}
	
	private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, token, user);//前缀，key，value
		Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);//name,value
		cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());//有效期
		cookie.setPath("/");//path为根目录
		response.addCookie(cookie);//写到response中就可以了
	}

}
