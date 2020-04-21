package club.chillman.eduorder.client.impl;

import club.chillman.commonutils.orderVo.UcenterMemberOrder;
import club.chillman.eduorder.client.UcenterClient;
import club.chillman.servicebase.exception.ChillTeachException;
import org.springframework.stereotype.Component;

@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    //以下方法出错后才会执行
    @Override
    public UcenterMemberOrder getUserInfoOrder(String id) {
        throw new ChillTeachException(20001,"获取用户信息出错惹！");
    }


}
