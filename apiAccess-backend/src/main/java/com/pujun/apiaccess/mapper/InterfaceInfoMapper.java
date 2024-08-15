package com.pujun.apiaccess.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pujun.apiaccesscommon.entity.InterfaceInfo;

import java.util.List;


/**
* @author JD
* @description 针对表【interface_info(接口信息)】的数据库操作Mapper
* @createDate 2024-08-01 15:37:56
* @Entity com.pujun.apiaccess.model.entity.InterfaceInfo
*/
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    List<InterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




