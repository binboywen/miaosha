package com.imooc.miaosha_2.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imooc.miaosha_2.domain.MiaoshaUser;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	public MiaoshaUser getById(@Param("id")long id);
}
