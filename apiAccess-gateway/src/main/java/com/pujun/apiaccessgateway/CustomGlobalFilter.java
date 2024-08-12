package com.pujun.apiaccessgateway;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.pujun.apiaccessinterfacesdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
            return handleNoAuth(response);
        }
        //  3. 用户鉴权（判断 ak、sk 是否合法）
//        HttpHeaders headers = request.getHeaders();
//         headers.get("accessKey");
//        String accessKey = headers.getFirst("accessKey");
//        String sign = headers.getFirst("sign");
//        String body = headers.getFirst("body");
//        String nonce = headers.getFirst("nonce");
//        String timestamp = headers.getFirst("timestamp");
//        if(StrUtil.hasBlank(accessKey, sign, body, nonce, timestamp)){
//            return handleNoAuth(exchange.getResponse());
//        }
//        //TODO 实际情况是根据accessKey去数据库查是否已给该用户分配签名
//        // 假设查到的 secret为 abc，进行加密得到 sign
//        String secretKey = "abc";
//        String sign1 = SignUtil.getSign(body, secretKey);
//        if(!StrUtil.equals(sign,sign1)){ //签名不正确
//            return handleNoAuth(response);
//        }
//        if(!accessKey.equals("pujun")){
//            return handleNoAuth(response);
//        }
//        if(Long.parseLong(nonce) > 10000){
//            return handleNoAuth(response);
//        }
//        //时间戳是否是数字
//        if(!NumberUtil.isNumber(timestamp)){
//            return handleNoAuth(response);
//        }
//        // 时间戳 不能和当前时间超过5分钟
//        final long FIVE_MINITE = 300 * 1000;
//        if(System.currentTimeMillis() - Long.parseLong(timestamp) > FIVE_MINITE){
//            return handleNoAuth(response);
//        }

        //  4. 请求的模拟接口是否存在？
        // TODO 从数据库中查询模拟接口（URL）是否存在，以及方法是否匹配（还可以校验请求参数）

        //  5. 请求转发，调用模拟接口
        Mono<Void> filter = chain.filter(exchange);
        //  6. 响应日志
        log.info("响应状态码：{}", response.getStatusCode());
        if(response.getStatusCode() == HttpStatus.OK){
        //  7. 调用成功，接口调用次数 + 1 invokeCount
        } else {
        //  8. 调用失败，返回一个规范的错误码
            return handleInvokeError(response);
        }
        return filter;
    }


    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain){
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // TODO 7. 调用成功，接口调用次数+1
                                // data从这个content中读取
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);// 释放掉内存
                                // 6.构建日志
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);// data
                                rspArgs.add(data);
                                log.info("<--- status:{} data:{}"// data
                                        , rspArgs.toArray());// log.info("<-- {} {}", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
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
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 无调用权限
     * @param response
     * @return
     */
    private Mono<Void> handleNoAuth(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}

