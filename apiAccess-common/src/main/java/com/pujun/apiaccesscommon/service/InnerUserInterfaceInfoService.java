package com.pujun.apiaccesscommon.service;


/**
* @author pujun
* @description 公共 用户接口 信息Service
* @createDate 2024-08-14 11:00:16
*/
public interface InnerUserInterfaceInfoService  {

    /**
     * 查看是否还有调用次数
     * @param userId
     * @param interfaceId
     * @return
     */
    boolean hasInvokeCount(long userId, long interfaceId);

    /**
     * 接口调用次数 + 1
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(long userId, long interfaceInfoId);
}
