package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class CampaignInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.title
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.comment
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.trader_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer traderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.goods_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.start_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date startAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.end_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date endAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.confirm_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Date confirmAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column campaign_info.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.id
     *
     * @return the value of campaign_info.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.id
     *
     * @param id the value for campaign_info.id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.title
     *
     * @return the value of campaign_info.title
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.title
     *
     * @param title the value for campaign_info.title
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.comment
     *
     * @return the value of campaign_info.comment
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.comment
     *
     * @param comment the value for campaign_info.comment
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.trader_id
     *
     * @return the value of campaign_info.trader_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getTraderId() {
        return traderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.trader_id
     *
     * @param traderId the value for campaign_info.trader_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.goods_id
     *
     * @return the value of campaign_info.goods_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.goods_id
     *
     * @param goodsId the value for campaign_info.goods_id
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.start_at
     *
     * @return the value of campaign_info.start_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.start_at
     *
     * @param startAt the value for campaign_info.start_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.end_at
     *
     * @return the value of campaign_info.end_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getEndAt() {
        return endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.end_at
     *
     * @param endAt the value for campaign_info.end_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.create_at
     *
     * @return the value of campaign_info.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.create_at
     *
     * @param createAt the value for campaign_info.create_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.update_at
     *
     * @return the value of campaign_info.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.update_at
     *
     * @param updateAt the value for campaign_info.update_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.confirm_at
     *
     * @return the value of campaign_info.confirm_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Date getConfirmAt() {
        return confirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.confirm_at
     *
     * @param confirmAt the value for campaign_info.confirm_at
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setConfirmAt(Date confirmAt) {
        this.confirmAt = confirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column campaign_info.valid
     *
     * @return the value of campaign_info.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column campaign_info.valid
     *
     * @param valid the value for campaign_info.valid
     *
     * @mbg.generated Wed Jan 30 23:45:49 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}