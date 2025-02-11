package org.javaboy.tienchin.contract.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author javaboy
 * @since 2025-02-11
 */
@TableName("tienchin_contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合同编号
     */
    @TableId(value = "contract_id", type = IdType.AUTO)
    private Integer contractId;

    /**
     * 客户手机号
     */
    private String phnoe;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 课程分类
     */
    private String type;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动ID
     */
    private Integer activityId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 合同状态(待审核 1， 已通过 2， 驳回 3)
     */
    private Integer status;

    /**
     * 合同文件地址
     */
    private String filePath;

    /**
     * 合同价格
     */
    private Object contractPrice;

    /**
     * 课程价格
     */
    private Object coursePrice;

    /**
     * 折扣类型
     */
    private Integer discountType;

    /**
     * 流程示例ID
     */
    private String processInstanceId;

    /**
     * 商机ID
     */
    private Integer businessId;

    private String createBy;

    private LocalDateTime createTime;

    private LocalDateTime updateBy;

    private LocalDateTime updateTime;

    private String remark;

    private Integer delFlag;

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public String getPhnoe() {
        return phnoe;
    }

    public void setPhnoe(String phnoe) {
        this.phnoe = phnoe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Object getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(Object contractPrice) {
        this.contractPrice = contractPrice;
    }

    public Object getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Object coursePrice) {
        this.coursePrice = coursePrice;
    }

    public Integer getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(LocalDateTime updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Contract{" +
            "contractId = " + contractId +
            ", phnoe = " + phnoe +
            ", name = " + name +
            ", type = " + type +
            ", activityName = " + activityName +
            ", activityId = " + activityId +
            ", courseName = " + courseName +
            ", courseId = " + courseId +
            ", channelId = " + channelId +
            ", channelName = " + channelName +
            ", status = " + status +
            ", filePath = " + filePath +
            ", contractPrice = " + contractPrice +
            ", coursePrice = " + coursePrice +
            ", discountType = " + discountType +
            ", processInstanceId = " + processInstanceId +
            ", businessId = " + businessId +
            ", createBy = " + createBy +
            ", createTime = " + createTime +
            ", updateBy = " + updateBy +
            ", updateTime = " + updateTime +
            ", remark = " + remark +
            ", delFlag = " + delFlag +
        "}";
    }
}
