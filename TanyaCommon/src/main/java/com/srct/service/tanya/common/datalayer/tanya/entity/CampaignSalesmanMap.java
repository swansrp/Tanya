package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class CampaignSalesmanMap {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.campaign_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer campaignId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.salesman_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer salesmanId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_salesman_map.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.id
     *
     * @return the value of campaign_salesman_map.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.id
     *
     * @param id the value for campaign_salesman_map.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.campaign_id
     *
     * @return the value of campaign_salesman_map.campaign_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getCampaignId() {
        return campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.campaign_id
     *
     * @param campaignId the value for campaign_salesman_map.campaign_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.salesman_id
     *
     * @return the value of campaign_salesman_map.salesman_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getSalesmanId() {
        return salesmanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.salesman_id
     *
     * @param salesmanId the value for campaign_salesman_map.salesman_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.create_at
     *
     * @return the value of campaign_salesman_map.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.create_at
     *
     * @param createAt the value for campaign_salesman_map.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.update_at
     *
     * @return the value of campaign_salesman_map.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.update_at
     *
     * @param updateAt the value for campaign_salesman_map.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_salesman_map.valid
     *
     * @return the value of campaign_salesman_map.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_salesman_map.valid
     *
     * @param valid the value for campaign_salesman_map.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}