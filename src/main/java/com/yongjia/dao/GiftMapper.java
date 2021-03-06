package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.Gift;

public interface GiftMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insert(Gift record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insertSelective(Gift record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Gift selectByPrimaryKey(Long id);
    
    List<Gift> selectByNameAndStatus(@Param("name")String name,@Param("status")Integer status, @Param("page")Map page);
    
    Long countByNameAndStatus(@Param("name")String name,@Param("status")Integer status);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeySelective(Gift record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeyWithBLOBs(Gift record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gift
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKey(Gift record);
}