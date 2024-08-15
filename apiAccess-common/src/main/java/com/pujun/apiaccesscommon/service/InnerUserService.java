package com.pujun.apiaccesscommon.service;

import com.pujun.apiaccesscommon.entity.User;

/**
 * 公共 用户 Service
 *
 * @author pujun
 */
public interface InnerUserService {

    /**
     * 根据accessKey去数据库查是否已给该用户分配签名
     * @param acceesKey
     */
    User getInvokeUser(String acceesKey);
}
