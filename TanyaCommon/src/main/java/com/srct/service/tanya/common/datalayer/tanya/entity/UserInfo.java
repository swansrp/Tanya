package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class UserInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.name
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.wechat_id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private String wechatId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.password
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.phone
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.email
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.create_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.update_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.last_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private Date lastAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_info.valid
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.id
     *
     * @return the value of user_info.id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.id
     *
     * @param id the value for user_info.id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.name
     *
     * @return the value of user_info.name
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.name
     *
     * @param name the value for user_info.name
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.wechat_id
     *
     * @return the value of user_info.wechat_id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public String getWechatId() {
        return wechatId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.wechat_id
     *
     * @param wechatId the value for user_info.wechat_id
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setWechatId(String wechatId) {
        this.wechatId = wechatId == null ? null : wechatId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.password
     *
     * @return the value of user_info.password
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.password
     *
     * @param password the value for user_info.password
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.phone
     *
     * @return the value of user_info.phone
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.phone
     *
     * @param phone the value for user_info.phone
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.email
     *
     * @return the value of user_info.email
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.email
     *
     * @param email the value for user_info.email
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.create_at
     *
     * @return the value of user_info.create_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.create_at
     *
     * @param createAt the value for user_info.create_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.update_at
     *
     * @return the value of user_info.update_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.update_at
     *
     * @param updateAt the value for user_info.update_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.last_at
     *
     * @return the value of user_info.last_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public Date getLastAt() {
        return lastAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.last_at
     *
     * @param lastAt the value for user_info.last_at
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setLastAt(Date lastAt) {
        this.lastAt = lastAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_info.valid
     *
     * @return the value of user_info.valid
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_info.valid
     *
     * @param valid the value for user_info.valid
     *
     * @mbg.generated Mon Jan 28 23:43:06 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}