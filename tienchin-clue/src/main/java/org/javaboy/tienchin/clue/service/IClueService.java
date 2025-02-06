package org.javaboy.tienchin.clue.service;

import org.javaboy.tienchin.clue.domain.Clue;
import com.baomidou.mybatisplus.extension.service.IService;
import org.javaboy.tienchin.clue.domain.vo.ClueDetails;
import org.javaboy.tienchin.clue.domain.vo.ClueSummary;
import org.javaboy.tienchin.common.core.domain.AjaxResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author javaboy
 * @since 2024-12-30
 */
public interface IClueService extends IService<Clue> {

    AjaxResult addclue(Clue clue);

    List<ClueSummary> selectClueList();

    AjaxResult getClueDetailsByClueId(Integer clueId);

    AjaxResult clueFollow(ClueDetails clueDetails);
}
