package com.pujun.apiaccess.service.impl;


import com.pujun.apiaccesscommon.entity.InterfaceInfo;
import com.pujun.apiaccesscommon.service.InnerInterfaceInfoService;
import com.pujun.apiaccesscommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceImplTest {
    @Resource
    InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    InnerInterfaceInfoService innerInterfaceInfoService;

    @Test
    void invokeInterfaceCount() {
        InterfaceInfo post = innerInterfaceInfoService.getInterfaceInfoByPath("http://localhost:8090/api/name/user/", "POST");
        System.out.println(post);
        boolean result = innerUserInterfaceInfoService.invokeInterfaceCount(1, 21);
        System.out.println(result);
        Assertions.assertTrue(result);
    }
}