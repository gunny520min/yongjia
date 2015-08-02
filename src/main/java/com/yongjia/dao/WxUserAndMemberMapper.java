package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.WxUserAndMember;

public interface WxUserAndMemberMapper {

    WxUserAndMember selectByOpenid(@Param("openid") String openid, @Param("pointPoolId") Long pointPoolId);

    List<WxUserAndMember> selectByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile,
            @Param("pointPoolId") Long pointPoolId, @Param("page") Map page);

    Long countByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile,
            @Param("pointPoolId") Long pointPoolId);

}