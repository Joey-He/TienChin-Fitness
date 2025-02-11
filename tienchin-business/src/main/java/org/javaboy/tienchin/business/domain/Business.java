package org.javaboy.tienchin.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.javaboy.tienchin.common.validator.CreateGroup;
import org.javaboy.tienchin.common.validator.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author javaboy
 * @since 2025-02-07
 */
@TableName("tienchin_business")
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商机编号
     */
    @NotNull(message = "{business.businessId.notnull}" , groups = EditGroup.class)
    @TableId(value = "business_id", type = IdType.AUTO)
    private Integer businessId;

    /**
     * 客户名字
     */
    @NotBlank(message = "{business.name.notblank}",groups = {EditGroup.class, CreateGroup.class})
    private String name;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 活动ID
     */
    private Integer activityId;

    /**
     * 0 男 1 女
     */
    private Integer gender;

    /**
     * 客户年龄
     */
    private Integer age;

    /**
     * 客户微信
     */
    private String weixin;

    /**
     * 客户QQ
     */
    private String qq;

    /**
     * 客户电话
     */
    @NotBlank(message = "{business.phone.notblank}",groups = {EditGroup.class, CreateGroup.class})
    private String phone;

    /**
     * 客户意向等级 1 近期报名 2 打算报名 考虑中 3 了解一下 4 打酱油
     */
    private Integer level;

    /**
     * 私教课程
     */
    private Integer subject;

    /**
     * 线索状态 1 已分配 2 跟进中 3 回收
     */
    private Integer status;

    /**
     * 0 存在 1删除
     */
    private Integer delFlag;

    /**
     * 伪线索失败的次数， 最大3次
     */
    private Integer failCount;

    /**
     * 下次跟进的时间
     */
    private LocalDateTime nextTime;

    /**
     * 线索失效时间
     */
    private LocalDateTime endTime;

    /**
     * 线索是否需要转派
     */
    private Byte transfer;

    private String remark;

    private LocalDateTime createTime;

    private String createBy;

    private LocalDateTime updateTime;

    private LocalDateTime updateBy;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 学历
     */
    private String education;

    /**
     * 身高
     */
    private Object height;

    /**
     * 体重
     */
    private Object weight;

    /**
     * 锻炼原因
     */
    private String reason;

    /**
     * 锻炼时间
     */
    private Integer hours;

    /**
     * 其它意向
     */
    private String otherIntention;

    /**
     * 线索id
     */
    private Integer clueId;

    private Double money;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    public void setNextTime(LocalDateTime nextTime) {
        this.nextTime = nextTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Byte getTransfer() {
        return transfer;
    }

    public void setTransfer(Byte transfer) {
        this.transfer = transfer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(LocalDateTime updateBy) {
        this.updateBy = updateBy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getOtherIntention() {
        return otherIntention;
    }

    public void setOtherIntention(String otherIntention) {
        this.otherIntention = otherIntention;
    }

    public Integer getClueId() {
        return clueId;
    }

    public void setClueId(Integer clueId) {
        this.clueId = clueId;
    }

    @Override
    public String toString() {
        return "Business{" +
            "businessId = " + businessId +
            ", name = " + name +
            ", channelId = " + channelId +
            ", activityId = " + activityId +
            ", gender = " + gender +
            ", age = " + age +
            ", weixin = " + weixin +
            ", qq = " + qq +
            ", phone = " + phone +
            ", level = " + level +
            ", subject = " + subject +
            ", status = " + status +
            ", delFlag = " + delFlag +
            ", failCount = " + failCount +
            ", nextTime = " + nextTime +
            ", endTime = " + endTime +
            ", transfer = " + transfer +
            ", remark = " + remark +
            ", createTime = " + createTime +
            ", createBy = " + createBy +
            ", updateTime = " + updateTime +
            ", updateBy = " + updateBy +
            ", province = " + province +
            ", city = " + city +
            ", area = " + area +
            ", courseId = " + courseId +
            ", occupation = " + occupation +
            ", education = " + education +
            ", height = " + height +
            ", weight = " + weight +
            ", reason = " + reason +
            ", hours = " + hours +
            ", otherIntention = " + otherIntention +
            ", clueId = " + clueId +
        "}";
    }
}
