package org.javaboy.tienchin.web.controller.tienchin.clue;

import org.javaboy.tienchin.clue.service.IFollowRecordService;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
@RestController
@RequestMapping("/tienchin/follow/record/clue")
public class FollowRecordController extends BaseController {

    @Autowired
    IFollowRecordService followRecordService;

    @PreAuthorize("hasAnyPermissions('tienchin:clue:veiw','tienchin:clue:follow')")
    @GetMapping("/{clueId}")
    public AjaxResult getFollowRecordByClueId(@PathVariable Integer clueId){
        return followRecordService.getFollowRecordByClueId(clueId);
    }
}
