package com.yongjia.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yongjia.model.CarModel;

public interface CarModelMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insert(CarModel record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long insertSelective(CarModel record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    CarModel selectByPrimaryKey(Long id);

    Long countByName(@Param("typeName") String typeName, @Param("modelName") String modelName);

    List<CarModel> selectByName(@Param("typeName") String typeName, @Param("modelName") String modelName,
            @Param("page") Map page);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKeySelective(CarModel record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    Long updateByPrimaryKey(CarModel record);
}