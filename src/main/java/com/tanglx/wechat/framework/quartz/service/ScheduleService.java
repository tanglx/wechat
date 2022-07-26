package com.tanglx.wechat.framework.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-26
 */
@Slf4j
@Service
public class ScheduleService {
    @Resource
    private Scheduler scheduler;
    private String defaultGroup = "default_group";

    public String scheduleJob(Class<? extends Job> jobBeanClass, String cron, String data) {
        // 创建需要执行的任务
        String jobName = UUID.randomUUID().toString();
        JobDetail jobDetail = JobBuilder.newJob(jobBeanClass)
                .withIdentity(jobName, defaultGroup)
                .usingJobData("data", data)
                .build();
        //创建触发器，指定任务执行时间
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, defaultGroup)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        //使用调度器进行任务调度
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            log.info("创建定时任务失败！");
        }
        return jobName;
    }

    public String scheduleFixTimeJob(Class<? extends Job> jobBeanClass, LocalDateTime startTime, String data) {
        //日期转CRON表达式
        //        String startCron = String.format("%d %d %d %d %d ? %d", DateUtil.second(startTime), DateUtil.minute(startTime), DateUtil.hour(startTime, true), DateUtil.dayOfMonth(startTime), DateUtil.month(startTime) + 1, DateUtil.year(startTime));
        String startCron = String.format("%d %d %d %d %d ? %d", startTime.getSecond(), startTime.getMinute(), startTime.getHour(), startTime.getDayOfMonth(), startTime.getMonth()
                .getValue(), startTime.getYear());
        return scheduleJob(jobBeanClass, startCron, data);
    }

    public Boolean cancelScheduleJob(String jobName) {
        boolean success = false;
        try {
            // 暂停触发器
            scheduler.pauseTrigger(new TriggerKey(jobName, defaultGroup));
            // 移除触发器中的任务
            scheduler.unscheduleJob(new TriggerKey(jobName, defaultGroup));
            // 删除任务
            scheduler.deleteJob(new JobKey(jobName, defaultGroup));
            success = true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return success;
    }

}
