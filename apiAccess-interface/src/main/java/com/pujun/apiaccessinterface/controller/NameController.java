package com.pujun.apiaccessinterface.controller;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import com.pujun.apiaccessinterfacesdk.model.User;
import com.pujun.apiaccessinterfacesdk.utils.SignUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 *  名称 API
 * @author pujun
 * @since 2024/8/2
 */
@RestController()
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "Get 你的名字是：" + name;
    }

    @GetMapping("/{name}")
    public String getNameByGet(@PathVariable(required = false) String name, HttpServletRequest request){
        System.out.println("request.getHeader(\"color\") = " + request.getHeader("color"));
        return "Get 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request){
//        String accessKey = request.getHeader("accessKey"); //5
//        String sign = request.getHeader("sign");
//        String body = request.getHeader("body");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        if(StrUtil.hasBlank(accessKey, sign, body, nonce, timestamp)){
//            throw new RuntimeException("无权限");
//        }
        //TODO 实际情况是根据accessKey去数据库查是否已给该用户分配签名
        // 假设查到的 secret为 abc，进行加密得到 sign
//        String secret = "abc";
//        String sign1 = SignUtil.getSign(body, secret);
//        if(!StrUtil.equals(sign,sign1)){ //签名不正确
//            throw new RuntimeException("无权限");
//        }
//        if(!accessKey.equals("pujun")){
//            throw new RuntimeException("无权限");
//        }
//        if(Long.parseLong(nonce) > 10000){
//            throw new RuntimeException("无权限");
//        }
//        //时间戳是否是数字
//        if(!NumberUtil.isNumber(timestamp)){
//            throw new RuntimeException("无权限");
//        }
//        // 时间戳 不能和当前时间超过5分钟
//        if(System.currentTimeMillis() - Long.parseLong(timestamp) > 300 * 1000){
//            throw new RuntimeException("无权限");
//        }

        return "Post 你的名字是：" + user.getUsername();
    }

}
