package com.pujun.apiaccess.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pujun.apiaccess.model.vo.UserInterfaceInfoVO;
import com.pujun.apiaccesscommon.entity.UserInterfaceInfo;

import java.util.List;


/**
* @author JD
* @description 针对表【user_interface_info(用户调用接口信息)】的数据库操作Mapper
* @createDate 2024-08-08 16:00:16
* @Entity com.pujun.apiaccess.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 得到top调用次数最多的接口
     * @param limit
     * @return
     */
    List<UserInterfaceInfoVO> listTopInvokeInterfaceInfo(int limit);
}




