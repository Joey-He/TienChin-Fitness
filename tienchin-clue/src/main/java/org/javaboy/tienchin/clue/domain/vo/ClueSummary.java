package org.javaboy.tienchin.clue.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.javaboy.tienchin.common.annotation.Excel;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class ClueSummary {
    /**
     * 线索编号
     */
    private Integer clueId;

    /**
     * 客户名字
     */
    @Excel(name = "客户名称")
    private String name;

    /**
     * 渠道ID
     */
    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 客户电话
     */
    @Excel(name = "客户电话")
    private String phone;


    /**
     * 线索状态 1 已分配 2 跟进中 3 回收 4 伪线索
     */
    @Excel(name = "线索状态", readConverterExp = "1=已分配,2=跟进中,3=回收,4=伪线索")
    private Integer status;

    @Excel(name = "创建时间")
    private LocalDateTime createTime;

    private String createBy;

    @Excel(name = "委派人")
    private String owner;

    @Excel(name = "下次跟进时间")
    private LocalDateTime nextTime;

    public Integer getClueId() {
        return clueId;
    }

    public void setClueId(Integer clueId) {
        this.clueId = clueId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getNextTime() {
        return nextTime;
    }

    public void setNextTime(LocalDateTime nextTime) {
        this.nextTime = nextTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "ClueSummary{" +
                "clueId=" + clueId +
                ", name='" + name + '\'' +
                ", channelName='" + channelName + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
