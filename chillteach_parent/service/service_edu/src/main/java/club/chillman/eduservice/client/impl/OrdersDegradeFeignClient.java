package club.chillman.eduservice.client.impl;

import club.chillman.commonutils.R;
import club.chillman.eduservice.client.OrdersClient;
import club.chillman.eduservice.client.VodClient;
import club.chillman.servicebase.exception.ChillTeachException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersDegradeFeignClient implements OrdersClient {
    //以下方法出错后才会执行
    @Override
    public boolean hasBoughtCourse(String courseId, String memberId) {
        throw new ChillTeachException(20001,"查询是否购买课程出错！");
    }


}
