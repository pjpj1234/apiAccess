package com.pujun.apiaccess.common;

import lombok.Data;
import java.io.Serializable;

/**
 * Id 接口请求
 *
 * @author pujun
 */
@Data
public class IdRequest implements Serializable {
    /**
     * 接口id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}