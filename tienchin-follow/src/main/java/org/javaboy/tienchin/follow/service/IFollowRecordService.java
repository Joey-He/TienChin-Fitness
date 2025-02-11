package org.javaboy.tienchin.follow.service;

import org.javaboy.tienchin.follow.domain.FollowRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.javaboy.tienchin.common.core.domain.AjaxResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
public interface IFollowRecordService extends IService<FollowRecord> {

    AjaxResult getFollowRecordByClueId(Integer clueId);

    AjaxResult getFollowRecordByBusinessId(Integer businessId);
}
