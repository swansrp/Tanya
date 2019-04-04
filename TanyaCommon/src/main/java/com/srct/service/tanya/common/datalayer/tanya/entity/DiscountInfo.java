package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class DiscountInfo {
    private Integer id;

    private String title;

    private String comment;

    private Integer factoryMetchatMapId;

    private Integer goodsId;

    private Integer goodsNumber;

    private Double amount;

    private Date startAt;

    private Date endAt;

    private Date createAt;

    private Date updateAt;

    private Byte confirmStatus;

    private Date confirmAt;

    private Integer confirmBy;

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

    public Integer getFactoryMetchatMapId() {
        return factoryMetchatMapId;
    }

    public void setFactoryMetchatMapId(Integer factoryMetchatMapId) {
        this.factoryMetchatMapId = factoryMetchatMapId;
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

    public Byte getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Byte confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getConfirmAt() {
        return confirmAt;
    }

    public void setConfirmAt(Date confirmAt) {
        this.confirmAt = confirmAt;
    }

    public Integer getConfirmBy() {
        return confirmBy;
    }

    public void setConfirmBy(Integer confirmBy) {
        this.confirmBy = confirmBy;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }
}