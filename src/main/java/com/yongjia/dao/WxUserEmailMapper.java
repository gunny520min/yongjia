package com.yongjia.dao;

import com.yongjia.model.WxUserEmail;

public interface WxUserEmailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int insert(WxUserEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int insertSelective(WxUserEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    WxUserEmail selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int updateByPrimaryKeySelective(WxUserEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(WxUserEmail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user_email
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int updateByPrimaryKey(WxUserEmail record);
}