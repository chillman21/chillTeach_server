package club.chillman.eduservice.client;

import club.chillman.eduservice.client.impl.OrdersDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-order",fallback = OrdersDegradeFeignClient.class)
public interface OrdersClient {
    //根据课程id和用户id查询订单表中的订单状态
    @GetMapping("/eduorder/order/hasBoughtCourse/{courseId}/{memberId}")
    public boolean hasBoughtCourse(@PathVariable("courseId") String courseId,
                                   @PathVariable("memberId") String memberId);
}
