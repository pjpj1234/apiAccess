package com.pujun.apiaccessinterface.client;


import com.pujun.apiaccessinterfacesdk.client.PuApiClient;
import com.pujun.apiaccessinterfacesdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;


@SpringBootTest
class PuApiClientTest {

    @Resource
    private PuApiClient puApiClient;

    @Test
    void test() throws UnsupportedEncodingException {
        PuApiClient puApiClient = new PuApiClient("pujun", "abc");
        String result1 = puApiClient.getNameByGet("pujun1");
        String result2 = puApiClient.getNameByPost("pujun2");
        User user = new User();
        user.setUsername("pujun3");
        String result3 = puApiClient.getUsernameByPostWithJSON(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    void testAPI() throws UnsupportedEncodingException {
        String result1 = puApiClient.getNameByGet("Tom");
        String result2 = puApiClient.getNameByPost("Tom2");
        User user = new User();
        user.setUsername("Tom3");
        String result3 = puApiClient.getUsernameByPostWithJSON(user);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

}