package com.yongjia.dao;

import com.yongjia.model.WxUser2member;

public interface WxUser2memberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insert(WxUser2member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insertSelective(WxUser2member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    WxUser2member selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeySelective(WxUser2member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_user2member
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKey(WxUser2member record);
}