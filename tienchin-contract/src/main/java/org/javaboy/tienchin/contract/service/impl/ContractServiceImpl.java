package org.javaboy.tienchin.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.javaboy.tienchin.business.domain.Business;
import org.javaboy.tienchin.business.service.IBusinessService;
import org.javaboy.tienchin.common.constant.TienChinConstants;
import org.javaboy.tienchin.common.core.domain.AjaxResult;
import org.javaboy.tienchin.common.core.domain.UploadFileResp;
import org.javaboy.tienchin.common.utils.SecurityUtils;
import org.javaboy.tienchin.contract.domain.Contract;
import org.javaboy.tienchin.contract.domain.vo.ContractInfo;
import org.javaboy.tienchin.contract.mapper.ContractMapper;
import org.javaboy.tienchin.contract.service.IContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.javaboy.tienchin.course.domain.Course;
import org.javaboy.tienchin.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author javaboy
 * @since 2025-02-11
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements IContractService {



    @Autowired
    IBusinessService businessService;

    @Autowired
    ICourseService courseService;

    @Autowired
    ContractMapper contractMapper;

    @Value("${tienchin.contract.file}")
    String contractFolder;

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    /**
     * 上传合同文件
     * @param file
     * @return
     */
    @Override
    public AjaxResult uploadContractFile(HttpServletRequest req, MultipartFile file) {
        String format = sdf.format(new Date());
        String fileDir = contractFolder + format;
        File dir = new File(fileDir);
        if (!dir.exists()) {
            //如果文件夹不存在，那么就将文件夹创建出来
            dir.mkdirs();
        }
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        //新的文件名
        String newName = UUID.randomUUID().toString() + "-" + originalFilename;
        try {
            file.transferTo(new File(dir, newName));
            // 通过HttpServletRequest动态获取url路径
            String url = req.getScheme() + "://"
                    + req.getServerName() + ":"
                    + req.getServerPort()
                    + req.getContextPath()
                    + "/tienchin/contract/views"
                    + format + newName;
            UploadFileResp resp = new UploadFileResp();
            resp.setName(originalFilename);
            resp.setUrl(url);
            return AjaxResult.success(resp);
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
        return AjaxResult.error("文件上传失败");
    }

    /**
     * 删除合同文件
     * @param year
     * @param month
     * @param day
     * @param name
     * @return
     */
    @Override
    public AjaxResult deleteContractFile(String year, String month, String day, String name) {
        String filename = contractFolder + File.separator + year + File.separator + month + File.separator + day + File.separator +name;
        File file = new File(filename);
        boolean delete = file.delete();
        return delete?AjaxResult.success("合同删除成功"):AjaxResult.error("合同删除失败");
    }

    /**
     * 添加合同
     * @param contract
     * @return
     */
    @Override
    @Transactional
    public AjaxResult addContract(Contract contract) {
        //1. 向合同表中添加数据
        //1.1 查询商机 ID 并设置
        QueryWrapper<Business> qw = new QueryWrapper<>();
        qw.lambda().eq(Business::getPhone, contract.getPhone()).orderByDesc(Business::getCreateTime);
        List<Business> list = businessService.list(qw);
        Integer businessId = list.get(0).getBusinessId();
        contract.setBusinessId(businessId);
        //1.2 查询课程价格并设置
        QueryWrapper<Course> cqw = new QueryWrapper<>();
        cqw.lambda().eq(Course::getCourseId, contract.getCourseId());
        Course c = courseService.getOne(cqw);
        contract.setCoursePrice(c.getPrice());
        //1.3 设置通用属性
        contract.setCreateBy(SecurityUtils.getUsername());
        contract.setCreateTime(LocalDateTime.now());
        contract.setDelFlag(0);
        contract.setStatus(TienChinConstants.CONTRACT_UNAPPROVE);
        save(contract);
        //2。 启动流程
        //2. 启动流程
        return AjaxResult.success("提交成功");
    }

    @Override
    public AjaxResult getConctractInfoByPhone(String phone) {
        List<ContractInfo> list = contractMapper.getConctractInfoByPhone(phone);
        if(list != null && !list.isEmpty()){
            ContractInfo contractInfo = list.get(0);
            return AjaxResult.success(contractInfo);
        }else {
            return AjaxResult.error("手机号码输入错误，客户不存在！");
        }
    }
}
