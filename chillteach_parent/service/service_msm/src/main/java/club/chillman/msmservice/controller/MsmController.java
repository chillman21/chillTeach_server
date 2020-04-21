package club.chillman.msmservice.controller;

import club.chillman.commonutils.R;
import club.chillman.msmservice.service.MsmService;
import club.chillman.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/message")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    //发送短信
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //从redis获取验证码，如果能获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok().message("您已获取过验证码，无须重复提交");
        }
        //如果redis获取不到，进行阿里云的短信方式
        //生成随机值，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        System.out.println(code);
        Map<String, Object> param = new HashMap<>();
        param.put("code",code);
        //调用service里面发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            System.out.println("------成功");
            return  R.ok().message("短信发送成功");
        }
        else return R.error().message("短信发送失败");
    }
}
