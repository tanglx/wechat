package com.tanglx.wechat.framework.quartz.job;

import com.tanglx.wechat.framework.quartz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-26
 */
@Slf4j
@Component
public class SendEmailJob extends QuartzJobBean {
    @Resource
    private ScheduleService scheduleService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String data = jobDataMap.getString("data");
        log.info("定时发送邮件操作：{}", data);
        //完成后删除触发器和任务
        scheduleService.cancelScheduleJob(trigger.getKey().getName());
    }
}