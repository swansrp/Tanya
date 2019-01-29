package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.Date;

public class GoodsInfo {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.id
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.comment
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private String comment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.photo_url
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private String photoUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.production
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private String production;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.spec
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private String spec;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.amount
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Double amount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.start_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Date startAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.end_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Date endAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Date createAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Date updateAt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column goods_info.valid
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    private Byte valid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.id
     *
     * @return the value of goods_info.id
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.id
     *
     * @param id the value for goods_info.id
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.title
     *
     * @return the value of goods_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.title
     *
     * @param title the value for goods_info.title
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.comment
     *
     * @return the value of goods_info.comment
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public String getComment() {
        return comment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.comment
     *
     * @param comment the value for goods_info.comment
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.photo_url
     *
     * @return the value of goods_info.photo_url
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.photo_url
     *
     * @param photoUrl the value for goods_info.photo_url
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.production
     *
     * @return the value of goods_info.production
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public String getProduction() {
        return production;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.production
     *
     * @param production the value for goods_info.production
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setProduction(String production) {
        this.production = production == null ? null : production.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.spec
     *
     * @return the value of goods_info.spec
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public String getSpec() {
        return spec;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.spec
     *
     * @param spec the value for goods_info.spec
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.amount
     *
     * @return the value of goods_info.amount
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.amount
     *
     * @param amount the value for goods_info.amount
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.start_at
     *
     * @return the value of goods_info.start_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.start_at
     *
     * @param startAt the value for goods_info.start_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.end_at
     *
     * @return the value of goods_info.end_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Date getEndAt() {
        return endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.end_at
     *
     * @param endAt the value for goods_info.end_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.create_at
     *
     * @return the value of goods_info.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.create_at
     *
     * @param createAt the value for goods_info.create_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.update_at
     *
     * @return the value of goods_info.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.update_at
     *
     * @param updateAt the value for goods_info.update_at
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column goods_info.valid
     *
     * @return the value of goods_info.valid
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public Byte getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column goods_info.valid
     *
     * @param valid the value for goods_info.valid
     *
     * @mbg.generated Tue Jan 29 15:18:35 CST 2019
     */
    public void setValid(Byte valid) {
        this.valid = valid;
    }
}