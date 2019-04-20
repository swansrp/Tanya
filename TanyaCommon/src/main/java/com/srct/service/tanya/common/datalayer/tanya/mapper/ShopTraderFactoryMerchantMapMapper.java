package com.srct.service.tanya.common.datalayer.tanya.mapper;

import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.ShopTraderFactoryMerchantMapExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tanyaShopTraderFactoryMerchantMapMapper")
public interface ShopTraderFactoryMerchantMapMapper {

    long countByExample(ShopTraderFactoryMerchantMapExample example);

    int deleteByExample(ShopTraderFactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ShopTraderFactoryMerchantMap record);

    int insertSelective(ShopTraderFactoryMerchantMap record);

    List<ShopTraderFactoryMerchantMap> selectByExample(ShopTraderFactoryMerchantMapExample example);

    ShopTraderFactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShopTraderFactoryMerchantMap record,
            @Param("example") ShopTraderFactoryMerchantMapExample example);

    int updateByExample(@Param("record") ShopTraderFactoryMerchantMap record,
            @Param("example") ShopTraderFactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(ShopTraderFactoryMerchantMap record);

    int updateByPrimaryKey(ShopTraderFactoryMerchantMap record);
}
