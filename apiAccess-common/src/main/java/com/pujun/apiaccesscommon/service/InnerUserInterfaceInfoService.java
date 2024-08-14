package com.pujun.apiaccesscommon.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pujun.apiaccesscommon.entity.UserInterfaceInfo;

/**
* @author pujun
* @description 公共 用户接口 信息Service
* @createDate 2024-08-14 11:00:16
*/
public interface InnerUserInterfaceInfoService  {

    /**
     * 从数据库中查询模拟接口（URL）是否存在，以及方法是否匹配
     * @param path
     * @param method
     * @return
     */
    UserInterfaceInfo getInterfaceInfoByPath(String path, String method);

    /**
     * 接口调用次数 + 1
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(long userId, long interfaceInfoId);
}
