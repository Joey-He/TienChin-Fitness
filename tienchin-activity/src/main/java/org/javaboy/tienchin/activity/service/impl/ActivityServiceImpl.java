package org.javaboy.tienchin.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.javaboy.tienchin.activity.domain.Activity;
import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.activity.mapper.ActivityMapper;
import org.javaboy.tienchin.activity.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2024-12-08
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    ActivityMapper activityMapper;

    /**
     * 查询活动
     * @return
     */
    @Override
    public List<ActivityVO> selectActivityList(ActivityVO activityVO) {
        expireActivity();
        return activityMapper.selectActivityList(activityVO);
    }

    /**
     * 添加活动
     * @param activityVO
     * @return
     */
    @Override
    public AjaxResult addChannel(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO,activity);
        activity.setCreateBy(SecurityUtils.getUsername());
        activity.setCreateTime(LocalDateTime.now());
        activity.setDelFlag(0);
        activity.setStatus(0);
        return save(activity)?AjaxResult.success("添加成功"):AjaxResult.error("添加失败");
    }

    /**
     * 修改活动
     * @param activityVO
     * @return
     */
    @Override
    public AjaxResult updateActivity(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO,activity);
        activity.setUpdateBy(SecurityUtils.getUsername());
        activity.setUpdateTime(LocalDateTime.now());
        //防止前端修改这三个属性
        activity.setCreateTime(null);
        activity.setCreateBy(null);
        activity.setDelFlag(null);
        return updateById(activity) ? AjaxResult.success("更新成功") : AjaxResult.error("更新失败");
    }

    /**
     * 根据渠道 ID 查询一个具体的活动
     * @param activityId
     * @return
     */
    @Override
    public ActivityVO getActivityById(Long activityId) {
        Activity activity = getById(activityId);
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activity,activityVO);
        return activityVO;
    }

    @Override
    public boolean deleteActivityByIds(Long[] activityIds) {
        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        uw.lambda().set(Activity::getDelFlag,1).in(Activity::getActivityId,activityIds);
        return update(uw);
    }

    @Override
    public AjaxResult selectActivityByChannelId(Integer channelId) {
        QueryWrapper<Activity> qw = new QueryWrapper<>();
        qw.lambda().eq(Activity::getChannelId,channelId).eq(Activity::getDelFlag,0);
        return AjaxResult.success(list(qw));
    }

    /**
     * 将超过endTime的活动设置为过期
     * @return
     */
    public boolean expireActivity(){
        UpdateWrapper<Activity> uw = new UpdateWrapper<>();
        //将原本状态为0，且endTime小于当前时间的活动设置为过期
        uw.lambda().set(Activity::getStatus,1).eq(Activity::getStatus,0).lt(Activity::getEndTime, LocalDateTime.now());
        return update(uw);
    }

}
