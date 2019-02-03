package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class OrderInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.title
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.comment
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.trader_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer traderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.factory_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer factoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.merchant_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer merchantId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.shop_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer shopId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.goods_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.goods_number
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer goodsNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.amount
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Double amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.campaign_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Integer campaignId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.start_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date startAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.end_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date endAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.factory_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date factoryConfirmAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.merchant_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Date merchantConfirmAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.id
     *
     * @return the value of order_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.id
     *
     * @param id the value for order_info.id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.title
     *
     * @return the value of order_info.title
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.title
     *
     * @param title the value for order_info.title
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.comment
     *
     * @return the value of order_info.comment
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.comment
     *
     * @param comment the value for order_info.comment
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.trader_id
     *
     * @return the value of order_info.trader_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getTraderId() {
        return traderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.trader_id
     *
     * @param traderId the value for order_info.trader_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.factory_id
     *
     * @return the value of order_info.factory_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getFactoryId() {
        return factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.factory_id
     *
     * @param factoryId the value for order_info.factory_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.merchant_id
     *
     * @return the value of order_info.merchant_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getMerchantId() {
        return merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.merchant_id
     *
     * @param merchantId the value for order_info.merchant_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.shop_id
     *
     * @return the value of order_info.shop_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.shop_id
     *
     * @param shopId the value for order_info.shop_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.goods_id
     *
     * @return the value of order_info.goods_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.goods_id
     *
     * @param goodsId the value for order_info.goods_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.goods_number
     *
     * @return the value of order_info.goods_number
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.goods_number
     *
     * @param goodsNumber the value for order_info.goods_number
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.amount
     *
     * @return the value of order_info.amount
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.amount
     *
     * @param amount the value for order_info.amount
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.campaign_id
     *
     * @return the value of order_info.campaign_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Integer getCampaignId() {
        return campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.campaign_id
     *
     * @param campaignId the value for order_info.campaign_id
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.start_at
     *
     * @return the value of order_info.start_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.start_at
     *
     * @param startAt the value for order_info.start_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.end_at
     *
     * @return the value of order_info.end_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getEndAt() {
        return endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.end_at
     *
     * @param endAt the value for order_info.end_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.create_at
     *
     * @return the value of order_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.create_at
     *
     * @param createAt the value for order_info.create_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.update_at
     *
     * @return the value of order_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.update_at
     *
     * @param updateAt the value for order_info.update_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.factory_confirm_at
     *
     * @return the value of order_info.factory_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getFactoryConfirmAt() {
        return factoryConfirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.factory_confirm_at
     *
     * @param factoryConfirmAt the value for order_info.factory_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setFactoryConfirmAt(Date factoryConfirmAt) {
        this.factoryConfirmAt = factoryConfirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.merchant_confirm_at
     *
     * @return the value of order_info.merchant_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Date getMerchantConfirmAt() {
        return merchantConfirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.merchant_confirm_at
     *
     * @param merchantConfirmAt the value for order_info.merchant_confirm_at
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setMerchantConfirmAt(Date merchantConfirmAt) {
        this.merchantConfirmAt = merchantConfirmAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order_info.valid
     *
     * @return the value of order_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order_info.valid
     *
     * @param valid the value for order_info.valid
     *
     * @mbg.generated Sun Feb 03 18:56:01 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}