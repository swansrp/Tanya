package com.srct.service.tanya.common.datalayer.tanya.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfo;
import com.srct.service.tanya.common.datalayer.tanya.entity.NotificationInfoExample;

@Component("tanyaNotificationInfoMapper")
public interface NotificationInfoMapper {

    long countByExample(NotificationInfoExample example);

    int deleteByExample(NotificationInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(NotificationInfo record);

    int insertSelective(NotificationInfo record);

    List<NotificationInfo> selectByExample(NotificationInfoExample example);

    NotificationInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") NotificationInfo record, @Param("example") NotificationInfoExample example);

    int updateByExample(@Param("record") NotificationInfo record, @Param("example") NotificationInfoExample example);

    int updateByPrimaryKeySelective(NotificationInfo record);

    int updateByPrimaryKey(NotificationInfo record);
}