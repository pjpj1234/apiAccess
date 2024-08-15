package com.pujun.apiaccess.service;

import com.pujun.apiaccesscommon.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author JDpost
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-01 15:37:56
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验有效性
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
