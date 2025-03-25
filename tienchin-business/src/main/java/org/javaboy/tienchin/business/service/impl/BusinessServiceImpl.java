package org.javaboy.tienchin.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.business.domain.Business;
import org.javaboy.tienchin.business.domain.vo.BusinessFollow;
import org.javaboy.tienchin.business.domain.vo.BusinessSummary;
import org.javaboy.tienchin.business.domain.vo.BusinessSummaryEnhance;
import org.javaboy.tienchin.business.domain.vo.BusinessVO;
import org.javaboy.tienchin.business.mapper.BusinessMapper;
import org.javaboy.tienchin.business.service.IBusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.assignment.domain.Assignment;
import org.javaboy.tienchin.assignment.service.IAssignmentService;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.domain.model.EchartsPoint;
import org.javaboy.tienchin.common.core.domain.model.LineChartData;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.follow.domain.FollowRecord;
import org.javaboy.tienchin.follow.service.IFollowRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2025-02-07
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Autowired
    BusinessMapper businessMapper;

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    IFollowRecordService followRecordService;
    /**
     * 查询商机
     * @return
     */
    @Override
    public List<BusinessSummary> selectBusinessList(BusinessVO businessVO) {
        return businessMapper.selectBusinessList(businessVO);
    }

    /**
     * 添加线索
     * @param business
     * @return
     */
    @Override
    @Transactional
    public AjaxResult addBusiness(Business business) {
            QueryWrapper<Business> qw = new QueryWrapper<>();
            qw.lambda().eq(Business::getPhone,business.getPhone());
            Business one = getOne(qw);
            if(one !=null){
                return AjaxResult.error("手机号码重复，商机录入失败");
            }
            business.setStatus(TienChinConstants.BUSINESS_ALLOCATED);
            business.setCreateBy(SecurityUtils.getUsername());
            business.setCreateTime(LocalDateTime.now());
            business.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOWTIME));
            //添加商机
            save(business);
            //添加默认分配人
            Assignment assignment = new Assignment();
            assignment.setAssignId(business.getBusinessId());
            assignment.setLatest(true);
            assignment.setType(TienChinConstants.BUSINESS_TYPE);
            assignment.setUserName(SecurityUtils.getUsername());
            assignment.setUserId(SecurityUtils.getUserId());
            assignment.setDeptId(SecurityUtils.getDeptId());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignmentService.save(assignment);
            return AjaxResult.success("商机录入成功！");
    }

    /**
     * 根据BusinessId获得商机信息
     * @param id
     * @return
     */
    @Override
    public AjaxResult getBusinessById(Integer id) {
        return AjaxResult.success(getById(id));
    }

    /**
     * 商机跟进
     * @param businessFollow
     * @return
     */
    @Override
    @Transactional
    public AjaxResult follow(BusinessFollow businessFollow) {
            // 1. 跟新business表
            Business business = new Business();
            BeanUtils.copyProperties(businessFollow,business);
            business.setUpdateTime(LocalDateTime.now());
            business.setCreateBy(SecurityUtils.getUsername());
            business.setStatus(TienChinConstants.BUSINESS_FOLLOWING);
            updateById(business);
            // 2. 插入follow_record数据
            FollowRecord followRecord = new FollowRecord();
            followRecord.setAssignId(businessFollow.getBusinessId());
            followRecord.setInfo(businessFollow.getInfo());
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecord.setType(TienChinConstants.BUSINESS_TYPE);
            followRecordService.save(followRecord);
            return AjaxResult.success("商机跟进成功！");
    }

    /**
     * 跟进businessI获取信息
     * @param businessId
     * @return
     */
    @Override
    public AjaxResult getBusinessSummaryBybusinessId(Integer businessId) {
        Business business = getById(businessId);
        BusinessSummaryEnhance businessSummaryEnhance = new BusinessSummaryEnhance();
        BeanUtils.copyProperties(business,businessSummaryEnhance);
        return AjaxResult.success(businessSummaryEnhance);
    }

    /**
     * 更新商机
     * @param businessSummaryEnhance
     * @return
     */
    @Override
    public AjaxResult updateBusiness(BusinessSummaryEnhance businessSummaryEnhance) {
        Business business = new Business();
        BeanUtils.copyProperties(businessSummaryEnhance,business);
        return updateById(business)?AjaxResult.success("更新成功！"):AjaxResult.error("更新失败");
    }

    /**
     * 删除商机
     * @param businessIds
     * @return
     */
    @Override
    public AjaxResult deleteByBusinessId(Integer[] businessIds) {
        UpdateWrapper<Business> uw = new UpdateWrapper<>();
        uw.lambda().set(Business::getDelFlag,1).in(Business::getBusinessId,businessIds);
        return update(uw)?AjaxResult.success("删除成功！"):AjaxResult.error("删除失败");
    }

    @Override
    public AjaxResult businessAnalysisData(BusinessVO businessVO) {
        if (businessVO.getParams().get("beginTime")==null||businessVO.getParams().get("endTime")==null) {
            businessVO.getParams().put("beginTime", LocalDateTime.now().minusWeeks(1));
            businessVO.getParams().put("endTime", LocalDateTime.now().plusWeeks(1));
        }
        List<EchartsPoint> increaseBusiness = businessMapper.increaseBusiness(businessVO);
        List<EchartsPoint> totalBusiness = businessMapper.totalBusiness(businessVO);
        LineChartData lineChartData = new LineChartData();
        lineChartData.setIncrease(increaseBusiness);
        lineChartData.setTotal(totalBusiness);
        return AjaxResult.success(lineChartData);
    }
}
