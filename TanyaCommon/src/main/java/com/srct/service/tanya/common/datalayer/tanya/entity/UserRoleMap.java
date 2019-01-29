package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class UserRoleMap {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.user_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.role_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_role_map.valid
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.id
     *
     * @return the value of user_role_map.id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.id
     *
     * @param id the value for user_role_map.id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.user_id
     *
     * @return the value of user_role_map.user_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.user_id
     *
     * @param userId the value for user_role_map.user_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.role_id
     *
     * @return the value of user_role_map.role_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.role_id
     *
     * @param roleId the value for user_role_map.role_id
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.create_at
     *
     * @return the value of user_role_map.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.create_at
     *
     * @param createAt the value for user_role_map.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.update_at
     *
     * @return the value of user_role_map.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.update_at
     *
     * @param updateAt the value for user_role_map.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_role_map.valid
     *
     * @return the value of user_role_map.valid
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_role_map.valid
     *
     * @param valid the value for user_role_map.valid
     *
     * @mbg.generated Tue Jan 29 15:18:40 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}