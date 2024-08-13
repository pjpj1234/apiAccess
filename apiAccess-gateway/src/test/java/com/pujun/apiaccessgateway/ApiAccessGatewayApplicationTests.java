package com.pujun.apiaccessgateway;

import com.pujun.apiaccess.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiAccessGatewayApplicationTests {
    @DubboReference
    private RpcDemoService rpcDemoService;

    @Test
    void contextLoads() {
        System.out.println("pujun");
    }

    @Test
    void testRpc() {
        System.out.println(rpcDemoService.getName("pujun"));
        System.out.println("pujun");
    }

}
