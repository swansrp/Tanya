package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class TraderInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trader_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.id
     *
     * @return the value of trader_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.id
     *
     * @param id the value for trader_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.title
     *
     * @return the value of trader_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.title
     *
     * @param title the value for trader_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.comment
     *
     * @return the value of trader_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.comment
     *
     * @param comment the value for trader_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.user_id
     *
     * @return the value of trader_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.user_id
     *
     * @param userId the value for trader_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.create_at
     *
     * @return the value of trader_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.create_at
     *
     * @param createAt the value for trader_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.update_at
     *
     * @return the value of trader_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.update_at
     *
     * @param updateAt the value for trader_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trader_info.valid
     *
     * @return the value of trader_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trader_info.valid
     *
     * @param valid the value for trader_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}