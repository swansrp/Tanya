package com.srct.service.tanya.common.datalayer.tanya.entity;

public class RoleInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.id
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.table_name
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    private String tableName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role_info.valid
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.id
     *
     * @return the value of role_info.id
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
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
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.title
     *
     * @return the value of role_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.title
     *
     * @param title the value for role_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.table_name
     *
     * @return the value of role_info.table_name
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role_info.table_name
     *
     * @param tableName the value for role_info.table_name
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role_info.valid
     *
     * @return the value of role_info.valid
     *
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
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
     * @mbg.generated Tue Jan 29 15:18:36 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}