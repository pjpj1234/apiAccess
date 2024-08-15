package com.pujun.apiaccess.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pujun.apiaccess.common.ErrorCode;
import com.pujun.apiaccess.exception.BusinessException;
import com.pujun.apiaccess.mapper.InterfaceInfoMapper;
import com.pujun.apiaccesscommon.entity.InterfaceInfo;
import com.pujun.apiaccesscommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
* @author 公共 实现类
* @createDate 2024-08-14 12:00:56
*/
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 从数据库中查询模拟接口（URL）是否存在，以及方法是否匹配
     * @param path
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfoByPath(String path, String method) {
        if(StringUtils.isAnyBlank(path, method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        QueryWrapper<InterfaceInfo> queryWrapper
                = new QueryWrapper<>();
        queryWrapper.eq("url", path).eq("method", method);
        return interfaceInfoMapper.selectOne(queryWrapper);
    }

}




