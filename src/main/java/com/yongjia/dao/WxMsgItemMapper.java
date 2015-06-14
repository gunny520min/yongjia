package com.yongjia.dao;

import java.util.List;

import com.yongjia.model.WxMsgItem;

public interface WxMsgItemMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int insert(WxMsgItem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int insertSelective(WxMsgItem record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
     * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
     */
    WxMsgItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
     * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
     */
    List<WxMsgItem> selectByMsgId(Integer msgId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int updateByPrimaryKeySelective(WxMsgItem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int updateByPrimaryKeyWithBLOBs(WxMsgItem record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table wx_msg_item
	 * @mbggenerated  Sun Jun 14 20:57:12 CST 2015
	 */
	int updateByPrimaryKey(WxMsgItem record);
}