package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.PotentialCustomerAndMember;

public interface PotentialCustomerAndMemberMapper {

    PotentialCustomerAndMember selectById(Long id);
    
    List<PotentialCustomerAndMember> selectByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile,
            @Param("page") Map page);

    Long countByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile);

}