package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.business.domain.vo.BusinessVO;
import org.javaboy.tienchin.business.service.IBusinessService;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.clue.domain.vo.ClueVO;
import org.javaboy.tienchin.clue.service.IClueService;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tienchin/analysis")
public class AnalysisController {

    @Autowired
    IClueService clueService;

    @Autowired
    IBusinessService businessService;

    @Autowired
    IChannelService channelService;

    @Autowired
    IActivityService activityService;

    /**
     * 获取线索分析数据
     * @param clueVO
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:analysis:clue')")
    @GetMapping("/clue")
    public AjaxResult clueAnalysisData(ClueVO clueVO){
        return  clueService.clueAnalysisData(clueVO);
    }

    /**
     * 获取商机分析数据
     * @param businessVO
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:analysis:business')")
    @GetMapping("/business")
    public AjaxResult businessAnalysisData(BusinessVO businessVO){
        return  businessService.businessAnalysisData(businessVO);
    }

    /**
     * 获取渠道分析数据
     * @param channelVO
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:analysis:channel')")
    @GetMapping("/channel")
    public AjaxResult channelAnalysisData(ChannelVO channelVO){
        return  channelService.channelAnalysisData(channelVO);
    }

    /**
     * 获取活动分析数据
     * @param activityVO
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:analysis:activity')")
    @GetMapping("/activity")
    public AjaxResult activityAnalysisData(ActivityVO activityVO){
        return  activityService.activityAnalysisData(activityVO);
    }

}
