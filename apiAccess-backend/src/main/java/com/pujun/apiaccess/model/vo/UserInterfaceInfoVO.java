package com.pujun.apiaccess.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口id及调用次数 视图
 *
 */
@Data
public class UserInterfaceInfoVO implements Serializable {
    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}