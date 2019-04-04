package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class TraderFactoryMerchantMap {
    private Integer id;

    private Integer traderId;

    private Integer factoryId;

    private Integer factoryMerchantMapId;

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

    public Integer getTraderId() {
        return traderId;
    }

    public void setTraderId(Integer traderId) {
        this.traderId = traderId;
    }

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public Integer getFactoryMerchantMapId() {
        return factoryMerchantMapId;
    }

    public void setFactoryMerchantMapId(Integer factoryMerchantMapId) {
        this.factoryMerchantMapId = factoryMerchantMapId;
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