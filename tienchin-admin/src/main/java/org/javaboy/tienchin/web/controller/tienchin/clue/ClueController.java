package org.javaboy.tienchin.web.controller.tienchin.clue;

import org.aspectj.weaver.loadtime.Aj;
import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.domain.vo.ClueDetails;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.clue.service.IClueService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.domain.entity.SysDept;
import org.javaboy.tienchin.common.core.domain.entity.SysUser;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.domain.vo.CourseVO;
import org.javaboy.tienchin.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
@RestController
@RequestMapping("/tienchin/clue")
public class ClueController extends BaseController {

    @Autowired
    IClueService clueService;

    @Autowired
    IChannelService channelService;

    @Autowired
    IActivityService activityService;

    @Autowired
    ISysUserService sysUserService;

    /**
     * 添加线索
     */
    @PreAuthorize("hasPermission('tienchin:clue:create')")
    @Log(title = "线索管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Clue clue) {
        return clueService.addclue(clue);
    }

    /**
     * 查询所有的渠道
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {
        return AjaxResult.success(channelService.list());
    }


    @PreAuthorize("hasPermission('tienchin:clue:create')")
    @GetMapping("/activity/{channelId}")
    public AjaxResult getActivityByChannelId(@PathVariable Integer channelId){
        return activityService.selectActivityByChannelId(channelId);

    }

    /**
     * 查询线索
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<ClueSummary> list = clueService.selectClueList();
        return getDataTable(list);
    }

    /**
     * 跟进部门id获取user信息
     * @param deptId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:assignment')")
    @GetMapping("/users/{deptId}")
    public AjaxResult getUsersByDeptId(@PathVariable long deptId){
        return sysUserService.getUsersByDeptId(deptId);
    }

    /**
     * 根据clueId获得线索详细信息
     * @param clueId
     * @return
     */
    @PreAuthorize("hasAnyPermissions('tienchin:clue:veiw','tienchin:clue:follow')")
    @GetMapping("/{clueId}")
    public AjaxResult getClueDetailsByClueId(@PathVariable Integer clueId){
        return clueService.getClueDetailsByClueId(clueId);
    }

    /**
     * 跟进线索(对线索进行跟进(修改线索信息))
     * @param clueDetails
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:follow')")
    @PostMapping("/follow")
    public AjaxResult clueFollow(@RequestBody ClueDetails clueDetails){
        return clueService.clueFollow(clueDetails);
    }
}
