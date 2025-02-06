package org.javaboy.tienchin.web.controller.tienchin;

import org.javaboy.tienchin.channel.domain.Channel;
import org.javaboy.tienchin.channel.domain.vo.ChannelVO;
import org.javaboy.tienchin.channel.service.IChannelService;
import org.javaboy.tienchin.common.annotation.Log;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.page.TableDataInfo;
import org.javaboy.tienchin.common.enums.BusinessType;
import org.javaboy.tienchin.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2024-11-28
 */
@RestController
@RequestMapping("/tienchin/channel")
public class ChannelController extends BaseController {

    @Autowired
    IChannelService iChannelService;

    @PreAuthorize("hasPermission('tienchin:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(ChannelVO channelVO) {
        startPage();
        List<Channel> list = iChannelService.selectChannelList(channelVO);
        return getDataTable(list);
    }

    /**
     * 添加渠道
     */
    @PreAuthorize("hasPermission('tienchin:channel:create')")
    @Log(title = "渠道管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ChannelVO channelVO) {
        return iChannelService.addChannel(channelVO) ;
    }

    /**
     * 修改渠道
     */
    @PreAuthorize("hasPermission('tienchin:channel:edit')")
    @Log(title = "渠道管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ChannelVO channelVO) {

        return iChannelService.updateChannel(channelVO);
    }

    /**
     * 根据渠道 ID 查询一个具体的渠道
     *
     * @param channelId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:channel:edit')")
    @GetMapping("/{channelId}")
    public AjaxResult getInfo(@PathVariable Long channelId) {
        return AjaxResult.success(iChannelService.getById(channelId));
    }
    /**
     * 删除渠道
     */
    @PreAuthorize("hasPermission('tienchin:channel:remove')")
    @Log(title = "渠道管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{channelIds}")
    public AjaxResult remove(@PathVariable Long[] channelIds) {
        return toAjax(iChannelService.deleteChannelByIds(channelIds));
    }

    /**
     * 数据导出
     * @param response
     * @param channelVO
     */
    @Log(title = "渠道管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasPermission('tienchin:channel:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ChannelVO channelVO) {
        List<Channel> list = iChannelService.selectChannelList(channelVO);
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.exportExcel(response, list, "渠道数据");
    }

    /**
     * 数据导入
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "渠道管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("hasPermission('tienchin:channel:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        List<Channel> channelList = util.importExcel(file.getInputStream());
        return AjaxResult.success(iChannelService.importChannel(channelList,updateSupport));
    }

    /**
     * 导入模板
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
            ExcelUtil<Channel> util = new ExcelUtil<Channel>(Channel.class);
        util.importTemplateExcel(response, "渠道数据");
    }

}
