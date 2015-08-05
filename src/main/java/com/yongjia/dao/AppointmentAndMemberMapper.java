package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.AppointmentAndMember;

public interface AppointmentAndMemberMapper {

    List<AppointmentAndMember> selectByStatus(@Param("status") Integer status, @Param("page") Map page);

    Long countByStatus(@Param("status") Integer status);
    
    List<AppointmentAndMember> selectByMemberId(Long memberId);
    
    AppointmentAndMember selectById(Long id);
}