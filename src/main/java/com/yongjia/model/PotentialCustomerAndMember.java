package com.yongjia.model;

public class PotentialCustomerAndMember {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.member_id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long memberId;

    private String connectMobile;

    private String name;
    
    private String mobile;

    private Integer sex;

    private String headImgurl;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.car_model
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String carModel;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.car_type
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String carType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.car_color
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String carColor;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.buy_time
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer buyType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.buy_for
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer buyFor;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.buy_budget
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String buyBudget;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.buy_date
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long buyDate;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.pay_type
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Integer payType;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.create_at
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long createAt;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.create_by
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.update_at
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long updateAt;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * potential_customer.update_by
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long updateBy;

    private Long serviceBy;
    private String serviceByName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getConnectMobile() {
        return connectMobile;
    }

    public void setConnectMobile(String connectMobile) {
        this.connectMobile = connectMobile;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getBuyFor() {
        return buyFor;
    }

    public void setBuyFor(Integer buyFor) {
        this.buyFor = buyFor;
    }

    public String getBuyBudget() {
        return buyBudget;
    }

    public void setBuyBudget(String buyBudget) {
        this.buyBudget = buyBudget;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getServiceBy() {
        return serviceBy;
    }

    public void setServiceBy(Long serviceBy) {
        this.serviceBy = serviceBy;
    }

    public String getServiceByName() {
        return serviceByName;
    }

    public void setServiceByName(String serviceByName) {
        this.serviceByName = serviceByName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

}