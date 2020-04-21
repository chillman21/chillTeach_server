package club.chillman.eduorder.client.impl;

import club.chillman.commonutils.orderVo.CourseWebVoOrder;
import club.chillman.eduorder.client.EduClient;
import club.chillman.servicebase.exception.ChillTeachException;
import org.springframework.stereotype.Component;

@Component
public class EduDegradeFeignClient implements EduClient {
    //以下方法出错后才会执行
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        throw new ChillTeachException(20001,"获取课程信息出错惹！");
    }


}
