package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.activity.domain.vo.ActivityVO;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.poi.ExcelUtil;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.domain.vo.CourseVO;
import org.javaboy.tienchin.course.service.ICourseService;
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
 * @since 2024-12-16
 */
@RestController
@RequestMapping("/tienchin/course")
public class CourseController extends BaseController {

    @Autowired
    ICourseService iCourseService;
    /**
     * 查询课程
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseVO courseVO) {
        startPage();
        List<Course> list = iCourseService.selectCourseList(courseVO);
        return getDataTable(list);
    }

    /**
     * 添加课程
     */
    @PreAuthorize("hasPermission('tienchin:course:create')")
    @Log(title = "课程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Course course) {
        return iCourseService.addCourse(course);
    }

    /**
     * 修改课程
     */
    @PreAuthorize("hasPermission('tienchin:course:edit')")
    @Log(title = "课程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Course course) {
        return iCourseService.updateCourse(course);
    }

    /**
     * 根据渠道 ID 查询一个具体的课程
     * @param courseId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:course:edit')")
    @GetMapping("/{courseId}")
    public AjaxResult getInfo(@PathVariable Long courseId) {
        return AjaxResult.success(iCourseService.getById(courseId));
    }

    /**
     * 删除活动
     */
    @PreAuthorize("hasPermission('tienchin:course:remove')")
    @Log(title = "课程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{courseIds}")
    public AjaxResult remove(@PathVariable Long[] courseIds) {
        return toAjax(iCourseService.deleteCourseByIds(courseIds));
    }

    /**
     * 数据导出
     * @param response
     * @param courseVO
     */
    @Log(title = "课程管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasPermission('tienchin:course:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, CourseVO courseVO) {
        List<Course> list = iCourseService.selectCourseList(courseVO);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程数据");
    }
}
