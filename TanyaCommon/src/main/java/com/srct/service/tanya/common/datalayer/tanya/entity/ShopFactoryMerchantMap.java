package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class ShopFactoryMerchantMap {
    private Integer id;

    private Integer shopId;

    private Integer factoryMetchatMapId;

    private Date startAt;

    private Date endAt;

    private Date createAt;

    private Date updateAt;

    private Byte valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getFactoryMetchatMapId() {
        return factoryMetchatMapId;
    }

    public void setFactoryMetchatMapId(Integer factoryMetchatMapId) {
        this.factoryMetchatMapId = factoryMetchatMapId;
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

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }
}