package com.yongjia.model;

public class PointPool {
    
    public static final int StatusOverdue = 0;
    public static final int StatusActive = 1;
    public static final int StatusNoActive = 2;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool.name
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String name;
    
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool.start_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long startAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool.end_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long endAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column point_pool.total_point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer totalPoint;
    
    private Integer registerPoint;
    private Integer memberPoint;
    private Integer carOwnerPoint;
    private Integer toBuyCarPoint;

    private Long createBy;
    private Long createAt;
    private Long updateBy;
    private Long updateAt;
    
    
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool.id
     *
     * @return the value of point_pool.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool.id
     *
     * @param id the value for point_pool.id
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool.name
     *
     * @return the value of point_pool.name
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool.name
     *
     * @param name the value for point_pool.name
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool.start_at
     *
     * @return the value of point_pool.start_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getStartAt() {
        return startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool.start_at
     *
     * @param startAt the value for point_pool.start_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool.end_at
     *
     * @return the value of point_pool.end_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getEndAt() {
        return endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool.end_at
     *
     * @param endAt the value for point_pool.end_at
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column point_pool.total_point
     *
     * @return the value of point_pool.total_point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Integer getTotalPoint() {
        return totalPoint;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column point_pool.total_point
     *
     * @param totalPoint the value for point_pool.total_point
     *
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getRegisterPoint() {
        return registerPoint;
    }

    public void setRegisterPoint(Integer registerPoint) {
        this.registerPoint = registerPoint;
    }

    public Integer getMemberPoint() {
        return memberPoint;
    }

    public void setMemberPoint(Integer memberPoint) {
        this.memberPoint = memberPoint;
    }

    public Integer getCarOwnerPoint() {
        return carOwnerPoint;
    }

    public void setCarOwnerPoint(Integer carOwnerPoint) {
        this.carOwnerPoint = carOwnerPoint;
    }

    public Integer getToBuyCarPoint() {
        return toBuyCarPoint;
    }

    public void setToBuyCarPoint(Integer toBuyCarPoint) {
        this.toBuyCarPoint = toBuyCarPoint;
    }
    
    
}