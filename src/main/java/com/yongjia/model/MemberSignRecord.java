package com.yongjia.model;

public class MemberSignRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member_sign_record.id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member_sign_record.member_id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    private Long memberId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column member_sign_record.sign_date
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    private String signDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_sign_record.id
     *
     * @return the value of member_sign_record.id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_sign_record.id
     *
     * @param id the value for member_sign_record.id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_sign_record.member_id
     *
     * @return the value of member_sign_record.member_id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_sign_record.member_id
     *
     * @param memberId the value for member_sign_record.member_id
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column member_sign_record.sign_date
     *
     * @return the value of member_sign_record.sign_date
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public String getSignDate() {
        return signDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column member_sign_record.sign_date
     *
     * @param signDate the value for member_sign_record.sign_date
     *
     * @mbggenerated Sun Aug 09 15:22:14 CST 2015
     */
    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}