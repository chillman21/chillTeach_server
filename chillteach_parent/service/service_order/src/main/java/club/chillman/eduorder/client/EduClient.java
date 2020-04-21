package club.chillman.eduorder.client;

import club.chillman.commonutils.orderVo.CourseWebVoOrder;
import club.chillman.eduorder.client.impl.EduDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-edu",fallback = EduDegradeFeignClient.class)
public interface EduClient {
    //根据课程id查询课程订单信息
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
