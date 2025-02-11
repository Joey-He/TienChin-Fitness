package org.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.business.domain.Business;
import org.javaboy.tienchin.business.service.IBusinessService;
import org.javaboy.tienchin.assignment.domain.Assignment;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.follow.domain.FollowRecord;
import org.javaboy.tienchin.clue.domain.vo.ClueDetails;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.clue.domain.vo.ClueVO;
import org.javaboy.tienchin.clue.mapper.ClueMapper;
import org.javaboy.tienchin.assignment.service.IAssignmentService;
import org.javaboy.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.follow.service.IFollowRecordService;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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
 * @since 2024-12-30
 */
@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements IClueService {

    @Autowired
    IAssignmentService assignmentService;

    @Autowired
    ClueMapper clueMapper;

    @Autowired
    IFollowRecordService followRecordService;

    @Autowired
    IBusinessService businessService;

    /**
     * 添加线索
     * @param clue
     * @return
     */
    @Override
    @Transactional
    public AjaxResult addclue(Clue clue) {
        QueryWrapper<Clue> qw = new QueryWrapper<>();
        qw.lambda().eq(Clue::getPhone,clue.getPhone());
        Clue one = getOne(qw);
        if(one != null){
            return AjaxResult.error("手机号码重复，线索录入失败");
        }
        clue.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOWTIME));
        clue.setCreateBy(SecurityUtils.getUsername());
        clue.setCreateTime(LocalDateTime.now());
       //添加线索
        save(clue);
        //默认的分配人
        Assignment assignment = new Assignment();
        assignment.setAssignId(clue.getClueId());
        assignment.setLatest(true);
        assignment.setType(TienChinConstants.CLUE_TYPE);
        assignment.setUserName(SecurityUtils.getUsername());
        assignment.setUserId(SecurityUtils.getUserId());
        assignment.setDeptId(SecurityUtils.getDeptId());
        assignment.setCreateBy(SecurityUtils.getUsername());
        assignment.setCreateTime(LocalDateTime.now());
        assignmentService.save(assignment);
        return AjaxResult.success("线索录入成功");
    }

    /**
     *  查询线索
     * @return
     */
    @Override
    public List<ClueSummary> selectClueList(ClueVO cluevo) {
        return clueMapper.selectClueList(cluevo);
    }

    /**
     * 根据ClueId获得线索详细信息
     * @param clueId
     * @return
     */
    @Override
    public AjaxResult getClueDetailsByClueId(Integer clueId) {
        ClueDetails cd = clueMapper.getClueDetailsByClueId(clueId);
        return AjaxResult.success(cd);
    }

    /**
     * 跟进线索
     * @param clueDetails
     * @return
     */
    @Override
    @Transactional
    public AjaxResult clueFollow(ClueDetails clueDetails) {
            //1. 跟新tienchin_clue表
            Clue clue = new Clue();
            BeanUtils.copyProperties(clueDetails,clue);
            clue.setStatus(TienChinConstants.CLUE_FOLLOWING);
            updateById(clue);
            //2. 插入tienchin_clue_follow记录
            FollowRecord followRecord = new FollowRecord();
            followRecord.setAssignId(clueDetails.getClueId());
            followRecord.setCreateTime(LocalDateTime.now());
            followRecord.setCreateBy(SecurityUtils.getUsername());
            followRecord.setInfo(clueDetails.getRecord());
            followRecord.setType(TienChinConstants.CLUE_TYPE);
            followRecordService.save(followRecord);
            return AjaxResult.success("线索跟进成功！");
    }

    /**
     * 无效线索设置
     * @param clueDetails
     * @return
     */
    @Override
    @Transactional
    public AjaxResult InvalidclueFollow(ClueDetails clueDetails) {
            //如果已经失败了三次了 这条线索直接变为一个伪线索
            Clue clue = getById(clueDetails.getClueId());
            if(clue.getFailCount() == 3){
                //无效线索已达极限
                UpdateWrapper<Clue> updateWrapper = new UpdateWrapper<>();
                updateWrapper.lambda().set(Clue::getStatus,TienChinConstants.CLUE_INVALIDATE).eq(Clue::getClueId,clueDetails.getClueId());
                update(updateWrapper);
                return AjaxResult.error("无效线索设置成功已达极限，变为伪线索！");
            }
            // 1.设置fail_count + 1
            UpdateWrapper<Clue> uw = new UpdateWrapper<>();
            uw.lambda().setSql("fail_count = fail_count + 1").eq(Clue::getClueId,clueDetails.getClueId());
            update(uw);
            // 2. 需要往线索记录表中添加一条记录
            FollowRecord record = new FollowRecord();
            record.setInfo(clueDetails.getRecord());
            record.setType(TienChinConstants.CLUE_TYPE);
            record.setCreateTime(LocalDateTime.now());
            record.setCreateBy(SecurityUtils.getUsername());
            record.setAssignId(clueDetails.getClueId());
            followRecordService.save(record);
            return AjaxResult.success("无效线索设置成功！");
    }

    /**
     * 根据ClueId查询线索
     * @param clueId
     * @return
     */
    @Override
    public AjaxResult getClueSummary(Integer clueId) {
        Clue clue = getById(clueId);
//        ClueSummary clueSummary = new ClueSummary();
//        BeanUtils.copyProperties(clue,clueSummary);
        return AjaxResult.success(clue);
    }

    /**
     * 修改线索
     * @param clue
     * @return
     */
    @Override
    public AjaxResult updateClue(Clue clue) {
        return updateById(clue) ? AjaxResult.success("修改线索成功！"):AjaxResult.error("修改线索失败");
    }

    /**
     * 删除线索
     * @param clueIds
     * @return
     */
    @Override
    public AjaxResult deleteByClueId(Integer[] clueIds) {
        UpdateWrapper<Clue> uw = new UpdateWrapper<>();
        uw.lambda().set(Clue::getDelFlag,1).in(Clue::getClueId,clueIds);
        return update(uw)?AjaxResult.success("删除成功！"):AjaxResult.error("删除失败");
    }

    /**
     * 线索转为商机
     * @param clueId
     * @return
     */
    @Override
    @Transactional
    public AjaxResult clue2Business(Integer clueId) {
            Clue clue = getById(clueId);
            Business business = new Business();
            BeanUtils.copyProperties(clue,business);
            business.setCreateBy(SecurityUtils.getUsername());
            business.setCreateTime(LocalDateTime.now());
            business.setEndTime(null);
            business.setFailCount(0);
            business.setNextTime(null);
            business.setRemark(null);
            business.setUpdateBy(null);
            business.setUpdateTime(null);
            business.setStatus(TienChinConstants.BUSINESS_ALLOCATED);
            business.setNextTime(LocalDateTime.now().plusHours(TienChinConstants.NEXT_FOLLOWTIME));

            // 1.删除线索
            UpdateWrapper<Clue> uw = new UpdateWrapper<>();
            uw.lambda().set(Clue::getDelFlag,1).eq(Clue::getClueId,clueId);
            update(uw);

            //2.添加商机
            businessService.save(business);

            //3.默认情况下将商机分配给admin
            Assignment assignment = new Assignment();
            assignment.setUserName(TienChinConstants.ADMIN_USERNAME);
            assignment.setUserId(TienChinConstants.ADMIN_ID);
            assignment.setType(TienChinConstants.BUSINESS_TYPE);
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setDeptId(TienChinConstants.ADMIN_DEPT_ID);
            assignment.setAssignId(business.getBusinessId());
            assignment.setLatest(true);
            assignmentService.save(assignment);

            return AjaxResult.success("成功转为商机！");
    }
}
