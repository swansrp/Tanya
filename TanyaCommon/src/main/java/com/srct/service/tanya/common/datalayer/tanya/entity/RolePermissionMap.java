package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class RolePermissionMap {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.perimission_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Integer perimissionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.role_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_permission_map.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.id
     *
     * @return the value of role_permission_map.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.id
     *
     * @param id the value for role_permission_map.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.perimission_id
     *
     * @return the value of role_permission_map.perimission_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Integer getPerimissionId() {
        return perimissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.perimission_id
     *
     * @param perimissionId the value for role_permission_map.perimission_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setPerimissionId(Integer perimissionId) {
        this.perimissionId = perimissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.role_id
     *
     * @return the value of role_permission_map.role_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.role_id
     *
     * @param roleId the value for role_permission_map.role_id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.create_at
     *
     * @return the value of role_permission_map.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.create_at
     *
     * @param createAt the value for role_permission_map.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.update_at
     *
     * @return the value of role_permission_map.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.update_at
     *
     * @param updateAt the value for role_permission_map.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_permission_map.valid
     *
     * @return the value of role_permission_map.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_permission_map.valid
     *
     * @param valid the value for role_permission_map.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}