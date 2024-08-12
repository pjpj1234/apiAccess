package com.pujun.apiaccessinterfacesdk.client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pujun.apiaccessinterfacesdk.model.User;
import com.pujun.apiaccessinterfacesdk.utils.SignUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PuApiClient {

    private static final String GATEWAY_HOST = "http://localhost:8090";

    private final String accessKey;
    private final String secretKey;

    public PuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.get(GATEWAY_HOST + "/api/get/", paramMap);
    }

    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        return HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
    }

    // 把传入的参数修改为 地址 或者 接口id，这样传入到body、请求头中，或者添加到url后面，调用对应的Post功能返回结果
    // 其实如果传入参数为地址 这里就可以直接访问那个地址，返回结果，但是那个地址就不是我开发的了
    public String getUsernameByPostWithJSON(User user) throws UnsupportedEncodingException {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user/")
                .addHeaders(getHeaders(json)) //添加请求头
                .body(json)
                .execute();
        System.out.println("response = " + httpResponse);
        System.out.println("status = " + httpResponse.getStatus());
        if(httpResponse.isOk()){
            return httpResponse.body();
        }
        return "fail";
    }

    /**
     * 得到请求头
     * @param body
     * @return
     * @throws UnsupportedEncodingException
     */
    private Map<String, String> getHeaders(String body) throws UnsupportedEncodingException {
        Map<String, String> header = new HashMap<>();
        String correctBody = URLEncoder.encode(body, StandardCharsets.UTF_8.name()); //转参数为utf-8格式

        header.put("accessKey", accessKey);
        //一定不能发送给前端
//        header.put("secretKey", secretKey);
        header.put("sign", SignUtil.getSign(correctBody, secretKey)); //生成签名
        // 防止中文乱码
        header.put("body", correctBody); //用户参数
        header.put("nonce", RandomUtil.randomNumbers(4)); //随机数  长度为4 该数为4位数
        header.put("timestamp", String.valueOf(System.currentTimeMillis())); //时间戳
        return header;
    }

}
