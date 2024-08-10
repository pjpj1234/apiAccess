package com.pujun.apiaccess.service.impl;

import com.pujun.apiaccess.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceImplTest {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;

    @Test
    void invokeInterfaceCount() {
        boolean result = userInterfaceInfoService.invokeInterfaceCount(1, 1);
        Assertions.assertTrue(result);
    }
}