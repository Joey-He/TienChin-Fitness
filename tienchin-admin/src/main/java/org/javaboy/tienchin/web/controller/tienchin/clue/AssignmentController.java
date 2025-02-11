package org.javaboy.tienchin.web.controller.tienchin.clue;

import org.javaboy.tienchin.assignment.domain.Assignment;
import org.javaboy.tienchin.assignment.service.IAssignmentService;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
@RestController
@RequestMapping("/tienchin/assignment")
public class AssignmentController extends BaseController {

    @Autowired
    IAssignmentService assignmentService;

    @PostMapping()
    @PreAuthorize("hasAnyPermissions('tienchin:clue:assignment','tienchin:business:assignment')")
    public AjaxResult assignClue(@Validated @RequestBody Assignment assignment){
        return assignmentService.assignClue(assignment);
    }
}
