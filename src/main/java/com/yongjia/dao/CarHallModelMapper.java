package com.yongjia.dao;

import com.yongjia.model.CarHallModel;

public interface CarHallModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long deleteByPrimaryKey(Long id);
    
    Long deleteByCarHallId(Long carHallId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insert(CarHallModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insertSelective(CarHallModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    CarHallModel selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeySelective(CarHallModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car_hall_model
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKey(CarHallModel record);
}