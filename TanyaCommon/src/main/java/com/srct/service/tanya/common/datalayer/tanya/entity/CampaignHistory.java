package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class CampaignHistory {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.comment
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.salesman_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer salesmanId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.campaign_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer campaignId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.number
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.url
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.create_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.update_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.confirm_status
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Byte confirmStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.confirm_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Date confirmAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.confirm_by
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer confirmBy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.rewards
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Integer rewards;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_history.valid
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.id
     *
     * @return the value of campaign_history.id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.id
     *
     * @param id the value for campaign_history.id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.comment
     *
     * @return the value of campaign_history.comment
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.comment
     *
     * @param comment the value for campaign_history.comment
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.salesman_id
     *
     * @return the value of campaign_history.salesman_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getSalesmanId() {
        return salesmanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.salesman_id
     *
     * @param salesmanId the value for campaign_history.salesman_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.campaign_id
     *
     * @return the value of campaign_history.campaign_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getCampaignId() {
        return campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.campaign_id
     *
     * @param campaignId the value for campaign_history.campaign_id
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.number
     *
     * @return the value of campaign_history.number
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.number
     *
     * @param number the value for campaign_history.number
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.url
     *
     * @return the value of campaign_history.url
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.url
     *
     * @param url the value for campaign_history.url
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.create_at
     *
     * @return the value of campaign_history.create_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.create_at
     *
     * @param createAt the value for campaign_history.create_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.update_at
     *
     * @return the value of campaign_history.update_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.update_at
     *
     * @param updateAt the value for campaign_history.update_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.confirm_status
     *
     * @return the value of campaign_history.confirm_status
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.confirm_status
     *
     * @param confirmStatus the value for campaign_history.confirm_status
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.confirm_at
     *
     * @return the value of campaign_history.confirm_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Date getConfirmAt() {
        return confirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.confirm_at
     *
     * @param confirmAt the value for campaign_history.confirm_at
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setConfirmAt(Date confirmAt) {
        this.confirmAt = confirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.confirm_by
     *
     * @return the value of campaign_history.confirm_by
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getConfirmBy() {
        return confirmBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.confirm_by
     *
     * @param confirmBy the value for campaign_history.confirm_by
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setConfirmBy(Integer confirmBy) {
        this.confirmBy = confirmBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.rewards
     *
     * @return the value of campaign_history.rewards
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Integer getRewards() {
        return rewards;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.rewards
     *
     * @param rewards the value for campaign_history.rewards
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setRewards(Integer rewards) {
        this.rewards = rewards;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_history.valid
     *
     * @return the value of campaign_history.valid
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_history.valid
     *
     * @param valid the value for campaign_history.valid
     *
     * @mbg.generated Wed Mar 20 21:29:21 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}