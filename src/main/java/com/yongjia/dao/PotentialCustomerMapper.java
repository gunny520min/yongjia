package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.PotentialCustomer;

public interface PotentialCustomerMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insert(PotentialCustomer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insertSelective(PotentialCustomer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    PotentialCustomer selectByPrimaryKey(Long id);
    
    List<PotentialCustomer> selectByMemberId(Long memberId);

//    List<PotentialCustomer> selectByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile, @Param("page") Map page);
//    
//    Long countByNameAndPhone(@Param("name") String name, @Param("mobile") String mobile);
    
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeySelective(PotentialCustomer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table potential_customer
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKey(PotentialCustomer record);
    
    Long grabCustomer(PotentialCustomer pCustomer);
}