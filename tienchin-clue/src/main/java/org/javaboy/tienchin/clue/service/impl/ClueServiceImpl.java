package org.javaboy.tienchin.clue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.clue.domain.Assignment;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.domain.FollowRecord;
import org.javaboy.tienchin.clue.domain.vo.ClueDetails;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.clue.mapper.ClueMapper;
import org.javaboy.tienchin.clue.service.IAssignmentService;
import org.javaboy.tienchin.clue.service.IClueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.clue.service.IFollowRecordService;
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

    @Override
    public List<ClueSummary> selectClueList() {
        return clueMapper.selectClueList();
    }

    /**
     * 根据ClueId获得线索详细信息
     * @param cludId
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
        try {
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
        } catch (BeansException e) {
            return AjaxResult.error("线索跟进失败");
        }
    }
}
