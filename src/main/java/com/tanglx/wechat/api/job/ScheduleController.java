package com.tanglx.wechat.api.job;

import com.tanglx.wechat.common.vo.base.ObjectRestResponse;
import com.tanglx.wechat.framework.quartz.job.CronProcessJob;
import com.tanglx.wechat.framework.quartz.job.SendEmailJob;
import com.tanglx.wechat.framework.quartz.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-07-26
 */
@Api(tags = "ScheduleController", value = "定时任务调度相关接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    private ScheduleService scheduleService;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @ApiOperation("定时发送")
    @PostMapping("/sendEmail")
    public ObjectRestResponse sendEmail(@RequestParam String date, @RequestParam String data) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, df);
        String jobName = scheduleService.scheduleFixTimeJob(SendEmailJob.class, localDateTime, data);
        return new ObjectRestResponse(jobName);
    }


    @ApiOperation("通过CRON表达式调度任务")
    @PostMapping("/scheduleJob")
    public ObjectRestResponse scheduleJob(@RequestParam String cron, @RequestParam String data) {
        String jobName = scheduleService.scheduleJob(CronProcessJob.class, cron, data);
        return new ObjectRestResponse(jobName);
    }

    @ApiOperation("取消定时任务")
    @PostMapping("/cancelScheduleJob")
    public ObjectRestResponse cancelScheduleJob(@RequestParam String jobName) {
        Boolean success = scheduleService.cancelScheduleJob(jobName);
        return new ObjectRestResponse(success);
    }
}
