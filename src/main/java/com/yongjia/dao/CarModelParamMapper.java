package com.yongjia.dao;

import com.yongjia.model.CarModelParam;

public interface CarModelParamMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int insert(CarModelParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int insertSelective(CarModelParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    CarModelParam selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int updateByPrimaryKeySelective(CarModelParam record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_model_param
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    int updateByPrimaryKey(CarModelParam record);
}