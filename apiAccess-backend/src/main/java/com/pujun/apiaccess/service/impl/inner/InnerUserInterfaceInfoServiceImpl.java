package com.pujun.apiaccess.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pujun.apiaccess.common.ErrorCode;
import com.pujun.apiaccess.exception.BusinessException;
import com.pujun.apiaccess.mapper.InterfaceInfoMapper;
import com.pujun.apiaccess.mapper.UserInterfaceInfoMapper;
import com.pujun.apiaccesscommon.entity.UserInterfaceInfo;
import com.pujun.apiaccesscommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
* @author 公共 实现类
* @createDate 2024-08-14 12:00:56
*/
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 调用次数是否大于0
     * @param userId
     * @param interfaceInfoId
     * @return
     */
    @Override
    public boolean hasInvokeCount(long userId, long interfaceInfoId) {
        if(userId <= 0 || interfaceInfoId <= 0){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId).eq("interfaceInfoId", interfaceInfoId)
                .gt("leftNum", 0);
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoMapper.selectOne(queryWrapper);
        if(userInterfaceInfo == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "参数有误或调用次数已用完");
        }
        return true;
    }

    /**
     * 接口调用次数 + 1
     * @param userId
     * @param interfaceInfoId
     * @return
     */
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
        int updateCount = userInterfaceInfoMapper.update(null, updateWrapper);
        return updateCount > 0;
    }
}




