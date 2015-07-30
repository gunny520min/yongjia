package com.yongjia.model;

public class PointPoolConfig {

    public static final int TypeRegister = 1;
    public static final int TypeCarOwn = 2;
    public static final int TypeBuyCar = 3;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool_config.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool_config.point_pool_id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer pointPoolId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool_config.type
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool_config.point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer point;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool_config.id
     *
     * @return the value of point_pool_config.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool_config.id
     *
     * @param id the value for point_pool_config.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool_config.point_pool_id
     *
     * @return the value of point_pool_config.point_pool_id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Integer getPointPoolId() {
        return pointPoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool_config.point_pool_id
     *
     * @param pointPoolId the value for point_pool_config.point_pool_id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setPointPoolId(Integer pointPoolId) {
        this.pointPoolId = pointPoolId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool_config.type
     *
     * @return the value of point_pool_config.type
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool_config.type
     *
     * @param type the value for point_pool_config.type
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool_config.point
     *
     * @return the value of point_pool_config.point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool_config.point
     *
     * @param point the value for point_pool_config.point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setPoint(Integer point) {
        this.point = point;
    }
}