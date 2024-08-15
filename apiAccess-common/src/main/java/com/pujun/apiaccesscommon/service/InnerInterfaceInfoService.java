package com.pujun.apiaccesscommon.service;
import com.pujun.apiaccesscommon.entity.InterfaceInfo;


/**
* @author pujun
* @description 公共 接口 信息Service
* @createDate 2024-08-14 11:11:16
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口（URL）是否存在，以及方法是否匹配
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfoByPath(String path, String method);
}
