package org.javaboy.tienchin.web.controller.tienchin.business;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.aspectj.weaver.loadtime.Aj;
import org.javaboy.tienchin.activity.service.IActivityService;
import org.javaboy.tienchin.business.domain.Business;
import org.javaboy.tienchin.business.domain.vo.BusinessFollow;
import org.javaboy.tienchin.business.domain.vo.BusinessSummary;
import org.javaboy.tienchin.business.domain.vo.BusinessSummaryEnhance;
import org.javaboy.tienchin.business.domain.vo.BusinessVO;
import org.javaboy.tienchin.business.service.IBusinessService;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.clue.domain.Clue;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;
import org.javaboy.tienchin.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2025-02-07
 */
@RestController
@RequestMapping("/tienchin/business")
public class BusinessController extends BaseController {

    @Autowired
    IBusinessService businessService;

    @Autowired
    IChannelService channelService;

    @Autowired
    IActivityService activityService;

    @Autowired
    ICourseService courseService;
    /**
     * 查询商机
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusinessVO businessVO) {
        startPage();
        List<BusinessSummary> list = businessService.selectBusinessList(businessVO);
        return getDataTable(list);
    }

    /**
     * 查询所有的渠道
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:create')")
    @GetMapping("/channels")
    public AjaxResult getAllChannels() {
        return AjaxResult.success(channelService.list());
    }

    /**
     * 根据渠道id查询活动信息
     * @param channelId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:create')")
    @GetMapping("/activity/{channelId}")
    public AjaxResult getActivityByChannelId(@PathVariable Integer channelId){
        return activityService.selectActivityByChannelId(channelId);
    }

    /**
     *  添加商机
     * @param business
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:create')")
    @Log(title = "商机管理", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult add(@Validated(CreateGroup.class) @RequestBody Business business){
        return businessService.addBusiness(business);
    }


    /**
     * 根据课程类型查询课程
     * @param type
     * @return
     */
    @PreAuthorize("hasAnyPermissions('tienchin:business:veiw','tienchin:business:follow')")
    @GetMapping("/course/{type}")
    public AjaxResult getCourseByCourseId(@PathVariable Integer type){
        return courseService.getCourseByCourseId(type);
    }

    /**
     * 根据BusinessId获得商机信息
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyPermissions('tienchin:business:veiw','tienchin:business:follow')")
    @GetMapping("/{id}")
    public AjaxResult getBusinessById(@PathVariable Integer id){
        return businessService.getBusinessById(id);
    }

    /**
     * 获取所有课程信息
     * @return
     */
    @PreAuthorize("hasAnyPermissions('tienchin:business:veiw','tienchin:business:follow')")
    @GetMapping("/all_course")
    public AjaxResult getAllCourse(){
        return AjaxResult.success(courseService.list());
    }

    /**
     * 商机跟进
     * @param businessFollow
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:follow')")
    @PostMapping("/follow")
    public AjaxResult follow(@Validated @RequestBody BusinessFollow businessFollow){
        return businessService.follow(businessFollow);
    }

    /**
     * 跟进businessI获取信息
     * @param businessId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:edit')")
    @GetMapping("/summary/{businessId}")
    public AjaxResult getBusinessSummaryBybusinessId(@PathVariable Integer businessId){
        return businessService.getBusinessSummaryBybusinessId(businessId);
    }

    /**
     * 修改商机
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:edit')")
    @PutMapping
    public AjaxResult updateBusiness(@Validated(EditGroup.class)@RequestBody BusinessSummaryEnhance businessSummaryEnhance){
        return businessService.updateBusiness(businessSummaryEnhance);
    }

    /**
     * 删除商机
     * @param businessIds
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:business:remove')")
    @DeleteMapping("/{businessIds}")
    public AjaxResult deleteByBusinessId(@PathVariable Integer[] businessIds){
        return businessService.deleteByBusinessId(businessIds);
    }
}
