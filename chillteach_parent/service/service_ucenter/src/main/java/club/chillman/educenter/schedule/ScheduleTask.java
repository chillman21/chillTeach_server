package club.chillman.educenter.schedule;

import club.chillman.commonutils.DateUtil;
import club.chillman.educenter.controller.UcenterMemberController;
import club.chillman.educenter.listener.OnLineCount;
import club.chillman.servicebase.exception.ChillTeachException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class ScheduleTask {

    //每三分钟执行一次
    @Scheduled(cron = "0 0/3 * * * ?")//不可写7位
    public void LoginTask(){
        AtomicInteger count = UcenterMemberController.getLoginCount();
        Integer newCount = count.intValue();
        System.out.println(newCount);

        try {
            //首先把最新实时在线人数写入文件
            String ap = ResourceUtils.getFile("classpath:club/chillman/").getAbsolutePath();
            BufferedWriter out = null;
            out = new BufferedWriter(new FileWriter(ap+ File.separator + DateUtil.formatDate(new Date())+".txt"));
            out.write(newCount+"");
            out.flush();
            out.close();


            //读取今日当前最大在线人数
            File file = new File(ap + File.separator + DateUtil.formatDate(new Date()) + "_max.txt");
            int oldCount = 0;
            if (file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                oldCount = Integer.parseInt(br.readLine().trim());
            }


            if (newCount>oldCount){
                //新的在线人数比最大在线人数大，写入
                BufferedWriter out2 = null;
                out2 = new BufferedWriter(new FileWriter(ap+ File.separator + DateUtil.formatDate(new Date())+"_max.txt"));
                out2.write(newCount+"");
                out2.flush();
                out2.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ChillTeachException(20001,"写入文本文件失败:"+e.getMessage());
        }
    }
}
