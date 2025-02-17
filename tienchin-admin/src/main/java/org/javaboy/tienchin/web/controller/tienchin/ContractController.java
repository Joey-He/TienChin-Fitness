package org.javaboy.tienchin.web.controller.tienchin;

import liquibase.pro.packaged.A;
import org.javaboy.tienchin.business.service.IBusinessService;
import org.javaboy.tienchin.common.core.controller.BaseController;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.contract.domain.Contract;
import org.javaboy.tienchin.contract.service.IContractService;
import org.javaboy.tienchin.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author javaboy
 * @since 2025-02-11
 */
@RestController
@RequestMapping("/tienchin/contract")
public class ContractController extends BaseController {

    @Autowired
    IContractService contractService;

    @Autowired
    ISysUserService userService;

    @Autowired
    IBusinessService businessService;

    /**
     * 上传合同
     * @param req
     * @param file
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:contract:create')")
    @PostMapping("/upload")
    public AjaxResult uploadContractFile(HttpServletRequest req,MultipartFile file){
        return contractService.uploadContractFile(req,file);
    }

    /**
     * 删除合同文件
     * @param year
     * @param month
     * @param day
     * @param name
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:contract:create')")
    @DeleteMapping("/{year}/{month}/{day}/{name}")
    public AjaxResult deleteContractFile(@PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String name){
        return contractService.deleteContractFile(year, month, day, name);
    }

    /**
     * 跟进部门id获取用户信息
     * @param deptId
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:contract:create')")
    @GetMapping("/users/{deptId}")
    public AjaxResult getUsersByDeptId(@PathVariable Long deptId){
        return userService.getUsersByDeptId(deptId);
    }

    /**
     * 添加合同
     * @param contract
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:contract:create')")
    @PostMapping
    public AjaxResult addContract(@RequestBody Contract contract){
        return contractService.addContract(contract);
    }

    /**
     * 根据手机号码获取用户信息
     * @param phone
     * @return
     */
    @PreAuthorize("hasPermission('tienchin:contract:create')")
    @GetMapping("/customer/{phone}")
    public AjaxResult getConctractInfoByPhone(@PathVariable String phone){
        return contractService.getConctractInfoByPhone(phone);
    }
}
