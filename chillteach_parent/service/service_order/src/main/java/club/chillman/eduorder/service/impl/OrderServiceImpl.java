package club.chillman.eduorder.service.impl;

import club.chillman.commonutils.OrderNoUtil;
import club.chillman.commonutils.orderVo.CourseWebVoOrder;
import club.chillman.commonutils.orderVo.UcenterMemberOrder;
import club.chillman.eduorder.client.EduClient;
import club.chillman.eduorder.client.UcenterClient;
import club.chillman.eduorder.pojo.Order;
import club.chillman.eduorder.mapper.OrderMapper;
import club.chillman.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-18
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String createOrder(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用根据课程id获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//支付状态 0 未支付 1 已支付
        order.setPayType(1);//支付类型，1为微信支付
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
