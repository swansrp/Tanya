package com.srct.service.tanya.common.datalayer.tanya.mapper;

import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopFactoryMerchantMapExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tanyaShopFactoryMerchantMapMapper")
public interface ShopFactoryMerchantMapMapper {

    long countByExample(ShopFactoryMerchantMapExample example);

    int deleteByExample(ShopFactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopFactoryMerchantMap record);

    int insertSelective(ShopFactoryMerchantMap record);

    List<ShopFactoryMerchantMap> selectByExample(ShopFactoryMerchantMapExample example);

    ShopFactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopFactoryMerchantMap record,
            @Param("example") ShopFactoryMerchantMapExample example);

    int updateByExample(@Param("record") ShopFactoryMerchantMap record,
            @Param("example") ShopFactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(ShopFactoryMerchantMap record);

    int updateByPrimaryKey(ShopFactoryMerchantMap record);
}
