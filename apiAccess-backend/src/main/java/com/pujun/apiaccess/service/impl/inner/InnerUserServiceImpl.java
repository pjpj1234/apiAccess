package com.pujun.apiaccess.service.impl.inner;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pujun.apiaccess.mapper.UserMapper;
import com.pujun.apiaccesscommon.entity.User;
import com.pujun.apiaccesscommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * 公共实现类
 *
 * @author pujun
 */
@DubboService
@Slf4j
public class InnerUserServiceImpl implements InnerUserService {

    @Override
    public User getInvokeUser(String s) {
        return null;
    }
}




