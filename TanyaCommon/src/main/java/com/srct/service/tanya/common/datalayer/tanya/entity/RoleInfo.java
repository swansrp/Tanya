package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class RoleInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.role
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private String role;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.module_name
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private String moduleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.comment
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.id
     *
     * @return the value of role_info.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.id
     *
     * @param id the value for role_info.id
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.role
     *
     * @return the value of role_info.role
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.role
     *
     * @param role the value for role_info.role
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.module_name
     *
     * @return the value of role_info.module_name
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.module_name
     *
     * @param moduleName the value for role_info.module_name
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.comment
     *
     * @return the value of role_info.comment
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.comment
     *
     * @param comment the value for role_info.comment
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.create_at
     *
     * @return the value of role_info.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.create_at
     *
     * @param createAt the value for role_info.create_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.update_at
     *
     * @return the value of role_info.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.update_at
     *
     * @param updateAt the value for role_info.update_at
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.valid
     *
     * @return the value of role_info.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.valid
     *
     * @param valid the value for role_info.valid
     *
     * @mbg.generated Tue Feb 12 15:40:56 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}