package com.pujun.apiaccess.service.impl;


import com.pujun.apiaccesscommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceImplTest {
    @Resource
    InnerUserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeInterfaceCount() {
        boolean result = userInterfaceInfoService.invokeInterfaceCount(1, 1);
        System.out.println(result);
        Assertions.assertTrue(result);
    }
}