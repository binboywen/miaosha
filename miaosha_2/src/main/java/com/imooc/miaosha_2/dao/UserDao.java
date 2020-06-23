package com.imooc.miaosha_2.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.imooc.miaosha_2.domain.User;
import org.springframework.stereotype.Repository;

//mybatis中的注解
@Repository
@Mapper
public interface UserDao {

	//不需要xml文件配置
	@Select("select * from user where id = #{id}")
	public User getById(@Param("id")int id	);

	@Insert("insert into user(id, name)values(#{id}, #{name})")
	public int insert(User user);
	
}
