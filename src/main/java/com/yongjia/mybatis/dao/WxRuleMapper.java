package com.yongjia.mybatis.dao;

import com.yongjia.mybatis.model.WxRule;

public interface WxRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    int insert(WxRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    int insertSelective(WxRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    WxRule selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    int updateByPrimaryKeySelective(WxRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_rule
     *
     * @mbggenerated Sun Jun 14 21:40:21 CST 2015
     */
    int updateByPrimaryKey(WxRule record);
}