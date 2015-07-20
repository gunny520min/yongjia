package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.WxUser;
import com.yongjia.model.WxUserAndMember;

public interface WxUserAndMemberMapper {

	List<WxUserAndMember> selectByNameAndPhone(@Param("name")String name, @Param("mobile")String mobile, @Param("page")Map page);
	
	Long countByNameAndPhone(@Param("name")String name, @Param("mobile")String mobile);

}