package com.yongjia.model;

public class CarHallModel {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column car_hall_model.id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * car_hall_model.car_hall_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long carHallId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * car_hall_model.car_model_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long carModelId;
    private String carModelName;
    private String params;

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * car_hall_model.id
     * 
     * @return the value of car_hall_model.id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * car_hall_model.id
     * 
     * @param id the value for car_hall_model.id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * car_hall_model.car_hall_id
     * 
     * @return the value of car_hall_model.car_hall_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getCarHallId() {
        return carHallId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * car_hall_model.car_hall_id
     * 
     * @param carHallId the value for car_hall_model.car_hall_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setCarHallId(Long carHallId) {
        this.carHallId = carHallId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * car_hall_model.car_model_id
     * 
     * @return the value of car_hall_model.car_model_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getCarModelId() {
        return carModelId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * car_hall_model.car_model_id
     * 
     * @param carModelId the value for car_hall_model.car_model_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setCarModelId(Long carModelId) {
        this.carModelId = carModelId;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}