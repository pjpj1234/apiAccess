package com.pujun.apiaccessgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiAccessGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAccessGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("to_baidu", r -> r.path("/") //这个是URL的path后缀
//                        .uri("http://www.baidu.com/"))
//                .route("topujun", r -> r.path("/user/login") //这个是URL的path后缀 会加入到下面uri的后缀
//                        .uri("http://211.159.150.239/"))
//                .route("topujun2", r -> r.host("*.myhost.org") // 这个是域名
//                        .uri("http://httpbin.org"))
//                .build();
//    }
}
