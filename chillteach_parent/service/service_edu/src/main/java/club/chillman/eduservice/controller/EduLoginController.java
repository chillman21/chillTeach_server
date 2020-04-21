package club.chillman.eduservice.controller;

import club.chillman.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {
    @PostMapping("/login")
    public R login(){
         return R.ok().data("token","admin");
    }
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://www.chillman.club/upload/2020/04/TIM%E6%88%AA%E5%9B%BE20200229015031-0d5634db8cbf45ddb8864b0f3b1d6981.jpg");
    }
}
