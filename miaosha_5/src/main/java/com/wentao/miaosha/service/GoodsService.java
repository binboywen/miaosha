package com.wentao.miaosha.service;

import java.util.List;

import com.wentao.miaosha.dao.GoodsDao;
import com.wentao.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wentao.miaosha.domain.MiaoshaGoods;

@Service
public class GoodsService {
	
	@Autowired
    GoodsDao goodsDao;
	
	public List<GoodsVo> listGoodsVo(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getGoodsVoByGoodsId(long goodsId) {
		return goodsDao.getGoodsVoByGoodsId(goodsId);
	}

	public int reduceStock(GoodsVo goods) {
		MiaoshaGoods g = new MiaoshaGoods();
		g.setGoodsId(goods.getId());
		int flag = goodsDao.reduceStock(g);
		return flag;
	}
	
	
	
}
