package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class SalesmanInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.rewards
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Integer rewards;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column salesman_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.id
     *
     * @return the value of salesman_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.id
     *
     * @param id the value for salesman_info.id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.title
     *
     * @return the value of salesman_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.title
     *
     * @param title the value for salesman_info.title
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.comment
     *
     * @return the value of salesman_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.comment
     *
     * @param comment the value for salesman_info.comment
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.user_id
     *
     * @return the value of salesman_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.user_id
     *
     * @param userId the value for salesman_info.user_id
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.rewards
     *
     * @return the value of salesman_info.rewards
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Integer getRewards() {
        return rewards;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.rewards
     *
     * @param rewards the value for salesman_info.rewards
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.create_at
     *
     * @return the value of salesman_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.create_at
     *
     * @param createAt the value for salesman_info.create_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.update_at
     *
     * @return the value of salesman_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.update_at
     *
     * @param updateAt the value for salesman_info.update_at
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column salesman_info.valid
     *
     * @return the value of salesman_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column salesman_info.valid
     *
     * @param valid the value for salesman_info.valid
     *
     * @mbg.generated Sat Feb 23 00:13:02 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}