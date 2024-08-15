package com.pujun.apiaccess.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pujun.apiaccesscommon.entity.UserInterfaceInfo;

/**
* @author pujun
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Service
* @createDate 2024-08-08 16:00:16
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 有效用户调用接口信息校验
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    boolean invokeInterfaceCount(long userId, long interfaceInfoId);
}
