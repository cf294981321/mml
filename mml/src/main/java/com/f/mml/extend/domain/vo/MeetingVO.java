package com.f.mml.extend.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 会议的相关信息
 */
@Data
public class MeetingVO {
    //会议名称
    private String meetingName;
    //召集人id
    private String caller;
    //联系人id
    private String contacter;
    //会议开始日期
    private String beginDate;
    //会议开始时间
    private String beginTime;
    //会议结束日期
    private String endDate;
    //会议结束时间
    private String endTime;
    //会议的内容
    private String desc;
    //总人数
    private int totalNumber;
    //会议成员
    private String hrmmembers;
    //补充的其他人
    private String otherNumber;
    //会议室状态  0：草稿,1：待审批,2:正常,3:退回,4:取消,5:结束
    private String meetingStatus;
    //会议室的名称
    private String meetingRoomName;
    //会议室设备
    private String equipment;
    //会议的类型
    private String meetingType;
    //参会人员
    private List<String> numbers;
    //召集人
    private String callerPerson;
    //联系人
    private String contacterPerson;
    // 0-未开始  1-正在进行的   2-结束
    private String status;
    // 0-今天  1-明天
    private String day;
    //会议持续时间
    private long mintues;

    //会议持续时间
    private Boolean isOpen = false;
}
