package club.chillman.staservice.client;

import club.chillman.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //查询某一天的注册人数
    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
    //查询某一天的最大在线人数
    @GetMapping("/educenter/member/countMaxLogin/{day}")
    public int countMaxLogin(@PathVariable("day") String day);
    //查询当前在线人数
    @GetMapping("/countNowLogin")
    public int countNowLogin();
}
