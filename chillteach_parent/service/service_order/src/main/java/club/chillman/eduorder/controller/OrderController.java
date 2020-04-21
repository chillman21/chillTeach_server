package club.chillman.eduorder.controller;


import club.chillman.commonutils.JwtUtils;
import club.chillman.commonutils.R;
import club.chillman.eduorder.pojo.Order;
import club.chillman.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    //生成订单的方法
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderId = orderService.createOrder(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderId);
    }
    //根据id查询订单
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
    //根据课程id和用户id查询订单表中的订单状态
    @GetMapping("/hasBoughtCourse/{courseId}/{memberId}")
    public boolean hasBoughtCourse(@PathVariable String courseId,
                                   @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count>0) return true;
        else return false;
    }




}

