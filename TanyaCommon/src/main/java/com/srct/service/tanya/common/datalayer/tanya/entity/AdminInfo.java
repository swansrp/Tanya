package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class AdminInfo {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.contact
     *
     * @mbg.generated
     */
    private String contact;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.comment
     *
     * @mbg.generated
     */
    private String comment;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.start_at
     *
     * @mbg.generated
     */
    private Date startAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.end_at
     *
     * @mbg.generated
     */
    private Date endAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.create_at
     *
     * @mbg.generated
     */
    private Date createAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.update_at
     *
     * @mbg.generated
     */
    private Date updateAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin_info.valid
     *
     * @mbg.generated
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.id
     *
     * @return the value of admin_info.id
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.id
     *
     * @param id the value for admin_info.id
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.title
     *
     * @return the value of admin_info.title
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.title
     *
     * @param title the value for admin_info.title
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.contact
     *
     * @return the value of admin_info.contact
     * @mbg.generated
     */
    public String getContact() {
        return contact;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.contact
     *
     * @param contact the value for admin_info.contact
     * @mbg.generated
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.comment
     *
     * @return the value of admin_info.comment
     * @mbg.generated
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.comment
     *
     * @param comment the value for admin_info.comment
     * @mbg.generated
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.user_id
     *
     * @return the value of admin_info.user_id
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.user_id
     *
     * @param userId the value for admin_info.user_id
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.start_at
     *
     * @return the value of admin_info.start_at
     * @mbg.generated
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.start_at
     *
     * @param startAt the value for admin_info.start_at
     * @mbg.generated
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.end_at
     *
     * @return the value of admin_info.end_at
     * @mbg.generated
     */
    public Date getEndAt() {
        return endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.end_at
     *
     * @param endAt the value for admin_info.end_at
     * @mbg.generated
     */
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.create_at
     *
     * @return the value of admin_info.create_at
     * @mbg.generated
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.create_at
     *
     * @param createAt the value for admin_info.create_at
     * @mbg.generated
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.update_at
     *
     * @return the value of admin_info.update_at
     * @mbg.generated
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.update_at
     *
     * @param updateAt the value for admin_info.update_at
     * @mbg.generated
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column admin_info.valid
     *
     * @return the value of admin_info.valid
     * @mbg.generated
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column admin_info.valid
     *
     * @param valid the value for admin_info.valid
     * @mbg.generated
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}
