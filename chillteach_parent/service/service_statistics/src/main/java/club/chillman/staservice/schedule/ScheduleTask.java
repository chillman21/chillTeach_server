package club.chillman.staservice.schedule;

import club.chillman.commonutils.DateUtil;
import club.chillman.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService staService;
    //每天凌晨一点,把前一天的数据进行添加
    @Scheduled(cron = "0 0 1 * * ?")//不可写7位
    public void task1(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
