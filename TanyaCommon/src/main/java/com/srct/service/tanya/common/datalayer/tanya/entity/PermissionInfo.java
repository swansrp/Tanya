package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class PermissionInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.parent_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.parent_ids
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String parentIds;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.permission
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String permission;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.resource_type
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String resourceType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.url
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.name
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.id
     *
     * @return the value of permission_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.id
     *
     * @param id the value for permission_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.parent_id
     *
     * @return the value of permission_info.parent_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.parent_id
     *
     * @param parentId the value for permission_info.parent_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.parent_ids
     *
     * @return the value of permission_info.parent_ids
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.parent_ids
     *
     * @param parentIds the value for permission_info.parent_ids
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.permission
     *
     * @return the value of permission_info.permission
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.permission
     *
     * @param permission the value for permission_info.permission
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.resource_type
     *
     * @return the value of permission_info.resource_type
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.resource_type
     *
     * @param resourceType the value for permission_info.resource_type
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.url
     *
     * @return the value of permission_info.url
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.url
     *
     * @param url the value for permission_info.url
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.name
     *
     * @return the value of permission_info.name
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.name
     *
     * @param name the value for permission_info.name
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.create_at
     *
     * @return the value of permission_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.create_at
     *
     * @param createAt the value for permission_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.update_at
     *
     * @return the value of permission_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.update_at
     *
     * @param updateAt the value for permission_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission_info.valid
     *
     * @return the value of permission_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission_info.valid
     *
     * @param valid the value for permission_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}