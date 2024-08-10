package com.pujun.apiaccess.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口状态码
 * 上线 - 1 下线 - 0
 */
public enum InterfaceInfoStatusEnum {
    ONLINE("上线", 1),
    OFFLINE("下线", 0);

    private final String text;

    private final int value;

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    InterfaceInfoStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 得到值列表
     * @return
     */
    public static List<Integer> getValues(){
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

}
