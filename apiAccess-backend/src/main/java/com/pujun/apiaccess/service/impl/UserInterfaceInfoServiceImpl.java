package com.pujun.apiaccess.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pujun.apiaccess.common.ErrorCode;
import com.pujun.apiaccess.exception.BusinessException;
import com.pujun.apiaccess.model.entity.UserInterfaceInfo;
import com.pujun.apiaccess.service.UserInterfaceInfoService;
import com.pujun.apiaccess.mapper.UserInterfaceInfoMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
* @author pujun
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Service实现
* @createDate 2024-08-08 16:00:16
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 这里用插件GenerateAllSetter方法生成即可
        Long id = userInterfaceInfo.getId();
        Long userId = userInterfaceInfo.getUserId();
        Long interfaceInfoId = userInterfaceInfo.getInterfaceInfoId();
        Integer totalNum = userInterfaceInfo.getTotalNum();
        Integer leftNum = userInterfaceInfo.getLeftNum();

        if (ObjectUtils.anyNull(userId, interfaceInfoId, totalNum, leftNum)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 更新时 参数不能为空
        if (!add) {
           if(ObjectUtils.isEmpty(id) || id <= 0){
               throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求接口不存在");
           }
        }
        // 有参数则校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户或接口不存在");
        }
        if (leftNum <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于0");
        }
    }

    @Override
    public boolean invokeInterfaceCount(long userId, long interfaceInfoId) {
        // TODO 应该添加事务、锁
        if(userId <= 0 || interfaceInfoId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", userId).eq("interfaceInfoId", interfaceInfoId)
                .gt("leftNum", 0)
                .setSql("leftNum = leftNum - 1, totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }


}




