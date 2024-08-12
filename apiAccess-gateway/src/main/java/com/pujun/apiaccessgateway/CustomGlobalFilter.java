package com.pujun.apiaccessgateway;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.pujun.apiaccessinterfacesdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤器
 * @author pujun
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "127.0.0.2","0:0:0:0:0:0:0:1");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        //  1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求唯一路径：" + request.getPath().value());
        log.info("请求唯一方法：" + request.getMethod());
        log.info("请求唯一参数：" + request.getQueryParams());
        log.info("请求唯一来源地址：" + request.getRemoteAddress());
        //  2. （黑白名单）
        String remoteAddress = request.getRemoteAddress().getHostString();
        ServerHttpResponse response = exchange.getResponse();
        if(!IP_WHITE_LIST.contains(remoteAddress)){
            return handleInvokeError(response);
        }
        //  4. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
         headers.get("accessKey");
        String accessKey = headers.getFirst("accessKey");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        if(StrUtil.hasBlank(accessKey, sign, body, nonce, timestamp)){
            return handleInvokeError(exchange.getResponse());
        }
        //TODO 实际情况是根据accessKey去数据库查是否已给该用户分配签名
        // 假设查到的 secret为 abc，进行加密得到 sign
        String secretKey = "abc";
        String sign1 = SignUtil.getSign(body, secretKey);
        if(!StrUtil.equals(sign,sign1)){ //签名不正确
            return handleInvokeError(response);
        }
        if(!accessKey.equals("pujun")){
            return handleInvokeError(response);
        }
        if(Long.parseLong(nonce) > 10000){
            return handleInvokeError(response);
        }
        //时间戳是否是数字
        if(!NumberUtil.isNumber(timestamp)){
            return handleInvokeError(response);
        }
        // 时间戳 不能和当前时间超过5分钟
        final long FIVE_MINITE = 300 * 1000;
        if(System.currentTimeMillis() - Long.parseLong(timestamp) > FIVE_MINITE){
            return handleInvokeError(response);
        }

        //  5. 请求的模拟接口是否存在？
        //  6. 请求转发，调用模拟接口
        //  7. 响应日志
        //  8. 调用成功，接口调用次数 + 1
        //  9. 调用失败，返回一个规范的错误码

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理调用错误
     * @param response
     * @return
     */
    private Mono<Void> handleInvokeError(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}

