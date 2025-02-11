package org.javaboy.tienchin.web.controller.tienchin.clue;


import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.clue.domain.vo.ClueDetails;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.clue.domain.vo.ClueVO;
import org.javaboy.tienchin.clue.service.IClueService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.poi.ExcelUtil;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Clue clue) {
        return clueService.addclue(clue);
    }

    /**
     * 根据clueId查询线索
     * @param clueId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:edit')")
    @Log(title = "线索管理", businessType = BusinessType.INSERT)
    @GetMapping("/summary/{clueId}")
    public AjaxResult getClueSummary(@PathVariable Integer clueId) {
        return clueService.getClueSummary(clueId);
    }

    /**
     * 修改线索
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:edit')")
    @PutMapping
    public AjaxResult updateClue(@Validated(EditGroup.class)@RequestBody Clue clue){
        return clueService.updateClue(clue);
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

    /**
     * 根据渠道id查询活动信息
     * @param channelId
     * @return
     */
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
    public TableDataInfo list(ClueVO clueVO) {
        startPage();
        List<ClueSummary> list = clueService.selectClueList(clueVO);
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

    @PreAuthorize("hasPermission('tienchin:clue:follow')")
    @PostMapping("/invalid")
    public AjaxResult InvalidclueFollow(@RequestBody ClueDetails clueDetails){
        return clueService.InvalidclueFollow(clueDetails);
    }

    /**
     * 删除线索
     * @param clueIds
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:remove')")
    @DeleteMapping("/{clueIds}")
    public AjaxResult deleteByClueId(@PathVariable Integer[] clueIds){
        return clueService.deleteByClueId(clueIds);
    }

    @Log(title = "线索管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasPermission('tienchin:clue:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClueVO clueVO) {
        List<ClueSummary> list = clueService.selectClueList(clueVO);
        ExcelUtil<ClueSummary> util = new ExcelUtil<ClueSummary>(ClueSummary.class);
        util.exportExcel(response, list, "线索数据");
    }

    /**
     * 线索转为商机
     * @param clueId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:clue:follow')")
    @PostMapping("/to_business/{clueId}")
     public AjaxResult clue2Business(@PathVariable Integer clueId){
        return clueService.clue2Business(clueId);
     }
}
