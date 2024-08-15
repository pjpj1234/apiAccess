package com.pujun.apiaccess.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pujun.apiaccess.annotation.AuthCheck;
import com.pujun.apiaccess.common.BaseResponse;
import com.pujun.apiaccess.common.ResultUtils;
import com.pujun.apiaccess.mapper.UserInterfaceInfoMapper;
import com.pujun.apiaccess.model.vo.InterfaceInfoVO;
import com.pujun.apiaccess.model.vo.UserInterfaceInfoVO;
import com.pujun.apiaccess.service.InterfaceInfoService;
import com.pujun.apiaccesscommon.entity.InterfaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析接口 控制器
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo(){
        List<UserInterfaceInfoVO> userInterfaceInfoVOList = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfoVO>> interfaceInfoObjMap = userInterfaceInfoVOList.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfoVO::getInterfaceInfoId));
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", interfaceInfoObjMap.keySet());
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        List<InterfaceInfoVO> interfaceInfoVOList = interfaceInfoList.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            Integer totalNum = interfaceInfoObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOList);
    }
}
