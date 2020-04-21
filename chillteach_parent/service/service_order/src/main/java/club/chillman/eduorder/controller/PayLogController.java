package club.chillman.eduorder.controller;


import club.chillman.commonutils.R;
import club.chillman.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
    //生成微信支付二维码
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息,包含二维码地址，还有其他信息
        Map map =payLogService.createNative(orderNo);
        System.out.println("返回二维码map集合："+map);
        return R.ok().data(map);
    }
    //查询支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        //返回信息,包含二维码地址，还有其他信息
        Map<String,String> map =payLogService.queryPayStatus(orderNo);
        System.out.println("查询订单状态map集合："+map);
        if (map == null){
            System.out.println("------支付失败1");
            return R.error().message("支付出错了..");
        }
        else if (map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表，更新订单表状态
            payLogService.updateOrderStatus(map);
            System.out.println("***支付成功了");
            return R.ok().message("支付成功");
        }
        System.out.println("------支付失败2");
        return R.ok().code(25000).message("支付中");
    }
}

