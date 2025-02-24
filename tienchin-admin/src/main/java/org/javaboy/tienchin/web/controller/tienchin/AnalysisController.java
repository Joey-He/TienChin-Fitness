package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.clue.domain.vo.ClueVO;
import org.javaboy.tienchin.clue.service.IClueService;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tienchin/analysis")
public class AnalysisController {

    @Autowired
    IClueService clueService;

    /**
     * 获取线索分析数据
     * @param clueVO
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:analysis:clue')")
    @GetMapping("/clue")
    public AjaxResult clueAnalysisData(ClueVO clueVO){
        return  clueService.clueAnalysisData(clueVO);
    }
}
