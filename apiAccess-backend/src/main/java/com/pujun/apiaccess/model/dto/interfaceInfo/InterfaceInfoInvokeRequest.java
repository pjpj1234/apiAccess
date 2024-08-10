package com.pujun.apiaccess.model.dto.interfaceInfo;

import lombok.Data;
import java.io.Serializable;

/**
 * 接口调用请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    private static final long serialVersionUID = -9017163943965454498L;
    /**
     * id
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;
}