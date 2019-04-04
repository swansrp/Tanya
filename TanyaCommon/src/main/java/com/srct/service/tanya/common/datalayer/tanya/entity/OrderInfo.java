package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class OrderInfo {
    private Integer id;

    private String title;

    private String comment;

    private Integer traderFactoryMerchantId;

    private Integer shopId;

    private Integer goodsId;

    private Integer goodsNumber;

    private Double amount;

    private Integer discountId;

    private Date startAt;

    private Date endAt;

    private Date createAt;

    private Date updateAt;

    private Byte factoryConfirmStatus;

    private Date factoryConfirmAt;

    private Integer factoryConfirmBy;

    private Byte merchantConfirmStatus;

    private Date merchantConfirmAt;

    private Integer merchantConfirmNumber;

    private Integer merchantConfirmBy;

    private Byte valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getTraderFactoryMerchantId() {
        return traderFactoryMerchantId;
    }

    public void setTraderFactoryMerchantId(Integer traderFactoryMerchantId) {
        this.traderFactoryMerchantId = traderFactoryMerchantId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Byte getFactoryConfirmStatus() {
        return factoryConfirmStatus;
    }

    public void setFactoryConfirmStatus(Byte factoryConfirmStatus) {
        this.factoryConfirmStatus = factoryConfirmStatus;
    }

    public Date getFactoryConfirmAt() {
        return factoryConfirmAt;
    }

    public void setFactoryConfirmAt(Date factoryConfirmAt) {
        this.factoryConfirmAt = factoryConfirmAt;
    }

    public Integer getFactoryConfirmBy() {
        return factoryConfirmBy;
    }

    public void setFactoryConfirmBy(Integer factoryConfirmBy) {
        this.factoryConfirmBy = factoryConfirmBy;
    }

    public Byte getMerchantConfirmStatus() {
        return merchantConfirmStatus;
    }

    public void setMerchantConfirmStatus(Byte merchantConfirmStatus) {
        this.merchantConfirmStatus = merchantConfirmStatus;
    }

    public Date getMerchantConfirmAt() {
        return merchantConfirmAt;
    }

    public void setMerchantConfirmAt(Date merchantConfirmAt) {
        this.merchantConfirmAt = merchantConfirmAt;
    }

    public Integer getMerchantConfirmNumber() {
        return merchantConfirmNumber;
    }

    public void setMerchantConfirmNumber(Integer merchantConfirmNumber) {
        this.merchantConfirmNumber = merchantConfirmNumber;
    }

    public Integer getMerchantConfirmBy() {
        return merchantConfirmBy;
    }

    public void setMerchantConfirmBy(Integer merchantConfirmBy) {
        this.merchantConfirmBy = merchantConfirmBy;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }
}