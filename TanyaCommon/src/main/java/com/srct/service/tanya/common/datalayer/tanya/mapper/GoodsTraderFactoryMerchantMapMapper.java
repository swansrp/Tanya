package com.srct.service.tanya.common.datalayer.tanya.mapper;

import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMap;
import com.srct.service.tanya.common.datalayer.tanya.entity.GoodsTraderFactoryMerchantMapExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tanyaGoodsTraderFactoryMerchantMapMapper")
public interface GoodsTraderFactoryMerchantMapMapper {

    long countByExample(GoodsTraderFactoryMerchantMapExample example);

    int deleteByExample(GoodsTraderFactoryMerchantMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodsTraderFactoryMerchantMap record);

    int insertSelective(GoodsTraderFactoryMerchantMap record);

    List<GoodsTraderFactoryMerchantMap> selectByExample(GoodsTraderFactoryMerchantMapExample example);

    GoodsTraderFactoryMerchantMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsTraderFactoryMerchantMap record,
            @Param("example") GoodsTraderFactoryMerchantMapExample example);

    int updateByExample(@Param("record") GoodsTraderFactoryMerchantMap record,
            @Param("example") GoodsTraderFactoryMerchantMapExample example);

    int updateByPrimaryKeySelective(GoodsTraderFactoryMerchantMap record);

    int updateByPrimaryKey(GoodsTraderFactoryMerchantMap record);
}
