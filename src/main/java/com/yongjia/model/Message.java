package com.yongjia.model;

public class Message {
    
    public static final int StatusStop = 1;
    public static final int StatusActive = 0;
    
    public static final int PositionActive = 1;
    public static final int PositionStop = 0;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column wx_msg_item.id
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long id;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column wx_msg_item.title
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String title;

    private Integer status;

    private Integer posMessage;
    private Integer posManager;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * wx_msg_item.descipition
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String descipition;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column wx_msg_item.author
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String author;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column wx_msg_item.pic
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String pic;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * wx_msg_item.create_at
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long createAt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * wx_msg_item.create_by
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long createBy;
    private String createByName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * wx_msg_item.modify_at
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long updateAt;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column
     * wx_msg_item.modify_by
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private Long updateBy;
    private String updateByName;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database column wx_msg_item.content
     * 
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    private String content;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPosMessage() {
        return posMessage;
    }

    public void setPosMessage(Integer posMessage) {
        this.posMessage = posMessage;
    }

    public Integer getPosManager() {
        return posManager;
    }

    public void setPosManager(Integer posManager) {
        this.posManager = posManager;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.id
     * 
     * @return the value of wx_msg_item.id
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column wx_msg_item.id
     * 
     * @param id the value for wx_msg_item.id
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.title
     * 
     * @return the value of wx_msg_item.title
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.title
     * 
     * @param title the value for wx_msg_item.title
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.descipition
     * 
     * @return the value of wx_msg_item.descipition
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getDescipition() {
        return descipition;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.descipition
     * 
     * @param descipition the value for wx_msg_item.descipition
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setDescipition(String descipition) {
        this.descipition = descipition;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.author
     * 
     * @return the value of wx_msg_item.author
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.author
     * 
     * @param author the value for wx_msg_item.author
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.pic
     * 
     * @return the value of wx_msg_item.pic
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getPic() {
        return pic;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column wx_msg_item.pic
     * 
     * @param pic the value for wx_msg_item.pic
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.create_at
     * 
     * @return the value of wx_msg_item.create_at
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.create_at
     * 
     * @param createAt the value for wx_msg_item.create_at
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.create_by
     * 
     * @return the value of wx_msg_item.create_by
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.create_by
     * 
     * @param createBy the value for wx_msg_item.create_by
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
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

    /**
     * This method was generated by MyBatis Generator. This method returns the value of the database column
     * wx_msg_item.content
     * 
     * @return the value of wx_msg_item.content
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the value of the database column
     * wx_msg_item.content
     * 
     * @param content the value for wx_msg_item.content
     * @mbggenerated Tue Jul 07 14:25:04 CST 2015
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }
    
}