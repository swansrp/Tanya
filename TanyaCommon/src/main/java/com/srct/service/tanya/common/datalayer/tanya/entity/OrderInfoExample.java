package com.srct.service.tanya.common.datalayer.tanya.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(String value1, String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(String value1, String value2) {
            addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdIsNull() {
            addCriterion("trader_factory_merchant_id is null");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdIsNotNull() {
            addCriterion("trader_factory_merchant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdEqualTo(Integer value) {
            addCriterion("trader_factory_merchant_id =", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdNotEqualTo(Integer value) {
            addCriterion("trader_factory_merchant_id <>", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdGreaterThan(Integer value) {
            addCriterion("trader_factory_merchant_id >", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("trader_factory_merchant_id >=", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdLessThan(Integer value) {
            addCriterion("trader_factory_merchant_id <", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdLessThanOrEqualTo(Integer value) {
            addCriterion("trader_factory_merchant_id <=", value, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdIn(List<Integer> values) {
            addCriterion("trader_factory_merchant_id in", values, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdNotIn(List<Integer> values) {
            addCriterion("trader_factory_merchant_id not in", values, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdBetween(Integer value1, Integer value2) {
            addCriterion("trader_factory_merchant_id between", value1, value2, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andTraderFactoryMerchantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("trader_factory_merchant_id not between", value1, value2, "traderFactoryMerchantId");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Integer value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Integer value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Integer value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Integer value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Integer> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Integer> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Integer value1, Integer value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberIsNull() {
            addCriterion("goods_number is null");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberIsNotNull() {
            addCriterion("goods_number is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberEqualTo(Integer value) {
            addCriterion("goods_number =", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberNotEqualTo(Integer value) {
            addCriterion("goods_number <>", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberGreaterThan(Integer value) {
            addCriterion("goods_number >", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_number >=", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberLessThan(Integer value) {
            addCriterion("goods_number <", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberLessThanOrEqualTo(Integer value) {
            addCriterion("goods_number <=", value, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberIn(List<Integer> values) {
            addCriterion("goods_number in", values, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberNotIn(List<Integer> values) {
            addCriterion("goods_number not in", values, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberBetween(Integer value1, Integer value2) {
            addCriterion("goods_number between", value1, value2, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andGoodsNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_number not between", value1, value2, "goodsNumber");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Double value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Double value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Double value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Double value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Double value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Double value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Double> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Double> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Double value1, Double value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Double value1, Double value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andDiscountIdIsNull() {
            addCriterion("discount_id is null");
            return (Criteria) this;
        }

        public Criteria andDiscountIdIsNotNull() {
            addCriterion("discount_id is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountIdEqualTo(Integer value) {
            addCriterion("discount_id =", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdNotEqualTo(Integer value) {
            addCriterion("discount_id <>", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdGreaterThan(Integer value) {
            addCriterion("discount_id >", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("discount_id >=", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdLessThan(Integer value) {
            addCriterion("discount_id <", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdLessThanOrEqualTo(Integer value) {
            addCriterion("discount_id <=", value, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdIn(List<Integer> values) {
            addCriterion("discount_id in", values, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdNotIn(List<Integer> values) {
            addCriterion("discount_id not in", values, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdBetween(Integer value1, Integer value2) {
            addCriterion("discount_id between", value1, value2, "discountId");
            return (Criteria) this;
        }

        public Criteria andDiscountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("discount_id not between", value1, value2, "discountId");
            return (Criteria) this;
        }

        public Criteria andStartAtIsNull() {
            addCriterion("start_at is null");
            return (Criteria) this;
        }

        public Criteria andStartAtIsNotNull() {
            addCriterion("start_at is not null");
            return (Criteria) this;
        }

        public Criteria andStartAtEqualTo(Date value) {
            addCriterion("start_at =", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotEqualTo(Date value) {
            addCriterion("start_at <>", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtGreaterThan(Date value) {
            addCriterion("start_at >", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtGreaterThanOrEqualTo(Date value) {
            addCriterion("start_at >=", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtLessThan(Date value) {
            addCriterion("start_at <", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtLessThanOrEqualTo(Date value) {
            addCriterion("start_at <=", value, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtIn(List<Date> values) {
            addCriterion("start_at in", values, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotIn(List<Date> values) {
            addCriterion("start_at not in", values, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtBetween(Date value1, Date value2) {
            addCriterion("start_at between", value1, value2, "startAt");
            return (Criteria) this;
        }

        public Criteria andStartAtNotBetween(Date value1, Date value2) {
            addCriterion("start_at not between", value1, value2, "startAt");
            return (Criteria) this;
        }

        public Criteria andEndAtIsNull() {
            addCriterion("end_at is null");
            return (Criteria) this;
        }

        public Criteria andEndAtIsNotNull() {
            addCriterion("end_at is not null");
            return (Criteria) this;
        }

        public Criteria andEndAtEqualTo(Date value) {
            addCriterion("end_at =", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotEqualTo(Date value) {
            addCriterion("end_at <>", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtGreaterThan(Date value) {
            addCriterion("end_at >", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtGreaterThanOrEqualTo(Date value) {
            addCriterion("end_at >=", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtLessThan(Date value) {
            addCriterion("end_at <", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtLessThanOrEqualTo(Date value) {
            addCriterion("end_at <=", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtIn(List<Date> values) {
            addCriterion("end_at in", values, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotIn(List<Date> values) {
            addCriterion("end_at not in", values, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtBetween(Date value1, Date value2) {
            addCriterion("end_at between", value1, value2, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotBetween(Date value1, Date value2) {
            addCriterion("end_at not between", value1, value2, "endAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNull() {
            addCriterion("update_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIsNotNull() {
            addCriterion("update_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAtEqualTo(Date value) {
            addCriterion("update_at =", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotEqualTo(Date value) {
            addCriterion("update_at <>", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThan(Date value) {
            addCriterion("update_at >", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("update_at >=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThan(Date value) {
            addCriterion("update_at <", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtLessThanOrEqualTo(Date value) {
            addCriterion("update_at <=", value, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtIn(List<Date> values) {
            addCriterion("update_at in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotIn(List<Date> values) {
            addCriterion("update_at not in", values, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtBetween(Date value1, Date value2) {
            addCriterion("update_at between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andUpdateAtNotBetween(Date value1, Date value2) {
            addCriterion("update_at not between", value1, value2, "updateAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusIsNull() {
            addCriterion("factory_confirm_status is null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusIsNotNull() {
            addCriterion("factory_confirm_status is not null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusEqualTo(Byte value) {
            addCriterion("factory_confirm_status =", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusNotEqualTo(Byte value) {
            addCriterion("factory_confirm_status <>", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusGreaterThan(Byte value) {
            addCriterion("factory_confirm_status >", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("factory_confirm_status >=", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusLessThan(Byte value) {
            addCriterion("factory_confirm_status <", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusLessThanOrEqualTo(Byte value) {
            addCriterion("factory_confirm_status <=", value, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusIn(List<Byte> values) {
            addCriterion("factory_confirm_status in", values, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusNotIn(List<Byte> values) {
            addCriterion("factory_confirm_status not in", values, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusBetween(Byte value1, Byte value2) {
            addCriterion("factory_confirm_status between", value1, value2, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("factory_confirm_status not between", value1, value2, "factoryConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtIsNull() {
            addCriterion("factory_confirm_at is null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtIsNotNull() {
            addCriterion("factory_confirm_at is not null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtEqualTo(Date value) {
            addCriterion("factory_confirm_at =", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtNotEqualTo(Date value) {
            addCriterion("factory_confirm_at <>", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtGreaterThan(Date value) {
            addCriterion("factory_confirm_at >", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtGreaterThanOrEqualTo(Date value) {
            addCriterion("factory_confirm_at >=", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtLessThan(Date value) {
            addCriterion("factory_confirm_at <", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtLessThanOrEqualTo(Date value) {
            addCriterion("factory_confirm_at <=", value, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtIn(List<Date> values) {
            addCriterion("factory_confirm_at in", values, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtNotIn(List<Date> values) {
            addCriterion("factory_confirm_at not in", values, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtBetween(Date value1, Date value2) {
            addCriterion("factory_confirm_at between", value1, value2, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmAtNotBetween(Date value1, Date value2) {
            addCriterion("factory_confirm_at not between", value1, value2, "factoryConfirmAt");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByIsNull() {
            addCriterion("factory_confirm_by is null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByIsNotNull() {
            addCriterion("factory_confirm_by is not null");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByEqualTo(Integer value) {
            addCriterion("factory_confirm_by =", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByNotEqualTo(Integer value) {
            addCriterion("factory_confirm_by <>", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByGreaterThan(Integer value) {
            addCriterion("factory_confirm_by >", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByGreaterThanOrEqualTo(Integer value) {
            addCriterion("factory_confirm_by >=", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByLessThan(Integer value) {
            addCriterion("factory_confirm_by <", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByLessThanOrEqualTo(Integer value) {
            addCriterion("factory_confirm_by <=", value, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByIn(List<Integer> values) {
            addCriterion("factory_confirm_by in", values, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByNotIn(List<Integer> values) {
            addCriterion("factory_confirm_by not in", values, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByBetween(Integer value1, Integer value2) {
            addCriterion("factory_confirm_by between", value1, value2, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andFactoryConfirmByNotBetween(Integer value1, Integer value2) {
            addCriterion("factory_confirm_by not between", value1, value2, "factoryConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusIsNull() {
            addCriterion("merchant_confirm_status is null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusIsNotNull() {
            addCriterion("merchant_confirm_status is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusEqualTo(Byte value) {
            addCriterion("merchant_confirm_status =", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusNotEqualTo(Byte value) {
            addCriterion("merchant_confirm_status <>", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusGreaterThan(Byte value) {
            addCriterion("merchant_confirm_status >", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("merchant_confirm_status >=", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusLessThan(Byte value) {
            addCriterion("merchant_confirm_status <", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusLessThanOrEqualTo(Byte value) {
            addCriterion("merchant_confirm_status <=", value, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusIn(List<Byte> values) {
            addCriterion("merchant_confirm_status in", values, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusNotIn(List<Byte> values) {
            addCriterion("merchant_confirm_status not in", values, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusBetween(Byte value1, Byte value2) {
            addCriterion("merchant_confirm_status between", value1, value2, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("merchant_confirm_status not between", value1, value2, "merchantConfirmStatus");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtIsNull() {
            addCriterion("merchant_confirm_at is null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtIsNotNull() {
            addCriterion("merchant_confirm_at is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtEqualTo(Date value) {
            addCriterion("merchant_confirm_at =", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtNotEqualTo(Date value) {
            addCriterion("merchant_confirm_at <>", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtGreaterThan(Date value) {
            addCriterion("merchant_confirm_at >", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtGreaterThanOrEqualTo(Date value) {
            addCriterion("merchant_confirm_at >=", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtLessThan(Date value) {
            addCriterion("merchant_confirm_at <", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtLessThanOrEqualTo(Date value) {
            addCriterion("merchant_confirm_at <=", value, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtIn(List<Date> values) {
            addCriterion("merchant_confirm_at in", values, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtNotIn(List<Date> values) {
            addCriterion("merchant_confirm_at not in", values, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtBetween(Date value1, Date value2) {
            addCriterion("merchant_confirm_at between", value1, value2, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmAtNotBetween(Date value1, Date value2) {
            addCriterion("merchant_confirm_at not between", value1, value2, "merchantConfirmAt");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberIsNull() {
            addCriterion("merchant_confirm_number is null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberIsNotNull() {
            addCriterion("merchant_confirm_number is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberEqualTo(Integer value) {
            addCriterion("merchant_confirm_number =", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberNotEqualTo(Integer value) {
            addCriterion("merchant_confirm_number <>", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberGreaterThan(Integer value) {
            addCriterion("merchant_confirm_number >", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_confirm_number >=", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberLessThan(Integer value) {
            addCriterion("merchant_confirm_number <", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_confirm_number <=", value, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberIn(List<Integer> values) {
            addCriterion("merchant_confirm_number in", values, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberNotIn(List<Integer> values) {
            addCriterion("merchant_confirm_number not in", values, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberBetween(Integer value1, Integer value2) {
            addCriterion("merchant_confirm_number between", value1, value2, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_confirm_number not between", value1, value2, "merchantConfirmNumber");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByIsNull() {
            addCriterion("merchant_confirm_by is null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByIsNotNull() {
            addCriterion("merchant_confirm_by is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByEqualTo(Integer value) {
            addCriterion("merchant_confirm_by =", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByNotEqualTo(Integer value) {
            addCriterion("merchant_confirm_by <>", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByGreaterThan(Integer value) {
            addCriterion("merchant_confirm_by >", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_confirm_by >=", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByLessThan(Integer value) {
            addCriterion("merchant_confirm_by <", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_confirm_by <=", value, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByIn(List<Integer> values) {
            addCriterion("merchant_confirm_by in", values, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByNotIn(List<Integer> values) {
            addCriterion("merchant_confirm_by not in", values, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByBetween(Integer value1, Integer value2) {
            addCriterion("merchant_confirm_by between", value1, value2, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andMerchantConfirmByNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_confirm_by not between", value1, value2, "merchantConfirmBy");
            return (Criteria) this;
        }

        public Criteria andValidIsNull() {
            addCriterion("valid is null");
            return (Criteria) this;
        }

        public Criteria andValidIsNotNull() {
            addCriterion("valid is not null");
            return (Criteria) this;
        }

        public Criteria andValidEqualTo(Byte value) {
            addCriterion("valid =", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotEqualTo(Byte value) {
            addCriterion("valid <>", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThan(Byte value) {
            addCriterion("valid >", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidGreaterThanOrEqualTo(Byte value) {
            addCriterion("valid >=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThan(Byte value) {
            addCriterion("valid <", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidLessThanOrEqualTo(Byte value) {
            addCriterion("valid <=", value, "valid");
            return (Criteria) this;
        }

        public Criteria andValidIn(List<Byte> values) {
            addCriterion("valid in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotIn(List<Byte> values) {
            addCriterion("valid not in", values, "valid");
            return (Criteria) this;
        }

        public Criteria andValidBetween(Byte value1, Byte value2) {
            addCriterion("valid between", value1, value2, "valid");
            return (Criteria) this;
        }

        public Criteria andValidNotBetween(Byte value1, Byte value2) {
            addCriterion("valid not between", value1, value2, "valid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}