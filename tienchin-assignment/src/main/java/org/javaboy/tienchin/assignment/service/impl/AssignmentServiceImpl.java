package org.javaboy.tienchin.assignment.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.assignment.domain.Assignment;
import org.javaboy.tienchin.assignment.mapper.AssignmentMapper;
import org.javaboy.tienchin.assignment.service.IAssignmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
@Service
public class AssignmentServiceImpl extends ServiceImpl<AssignmentMapper, Assignment> implements IAssignmentService {

    @Override
    @Transactional
    public AjaxResult assignClue(Assignment assignment) {
        try {
            // 1.先将一个线索所有分配记录中的latest设置为false
            UpdateWrapper<Assignment> uw = new UpdateWrapper<>();
            uw.lambda().set(Assignment::getLatest,false).eq(Assignment::getAssignId,assignment.getAssignId()).eq(Assignment::getType,assignment.getType());
            update(uw);
            // 2.分配线索
//            assignment.setType(TienChinConstants.CLUE_TYPE);
            assignment.setCreateTime(LocalDateTime.now());
            assignment.setCreateBy(SecurityUtils.getUsername());
            assignment.setLatest(true);
            save(assignment);
            return AjaxResult.success("分配线索成功!");
        } catch (Exception e) {
            return AjaxResult.error("分配线索失败" + e.getMessage());
        }
    }
}
