package com.pujun.apiaccess.rpc.impl;

import com.pujun.apiaccess.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

@DubboService
public class RpcDemoServiceImpl implements RpcDemoService {
    @Override
    public String getName(String name) {
        return "Rpc invoke " + name + " succeed " +
                RpcContext.getContext().getRemoteAddress();
    }
}
