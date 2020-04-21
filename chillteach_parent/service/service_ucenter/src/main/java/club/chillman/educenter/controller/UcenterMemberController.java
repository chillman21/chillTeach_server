package club.chillman.educenter.controller;


import club.chillman.commonutils.DateUtil;
import club.chillman.commonutils.R;
import club.chillman.educenter.pojo.UcenterMember;
import club.chillman.educenter.pojo.vo.RegisterVo;
import club.chillman.educenter.service.UcenterMemberService;
import club.chillman.educenter.utils.JwtUtils;
import club.chillman.commonutils.orderVo.UcenterMemberOrder;
import club.chillman.servicebase.exception.ChillTeachException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    private static AtomicInteger loginCount = new AtomicInteger(0);

    public static AtomicInteger getLoginCount() {
        return loginCount;
    }

    static {
        try {
            String ap = ResourceUtils.getFile("classpath:club/chillman/").getAbsolutePath();
            File file = new File(ap + File.separator + DateUtil.formatDate(new Date()) + ".txt");
            if (file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                String count = br.readLine().trim();
                if (StringUtils.isEmpty(count)){count ="0";}
                loginCount.set(Integer.parseInt(count));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登录
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member){
        //调用service方法实现登录
        //返回token值，用jwt生成
        String token = memberService.login(member);
        if (!StringUtils.isEmpty(token))loginCount.incrementAndGet();
        System.out.println("******"+loginCount);
        return R.ok().data("token",token);
    }
    //注册
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }
    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
    //根据用户id获取用户信息
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,memberOrder);
        return memberOrder;
    }
    //查询某一天的注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
         Integer count = memberService.countRegisterDay(day);
         return R.ok().data("count",count);
    }

    //查询某一天的最大在线人数
    @GetMapping("/countMaxLogin/{day}")
    public int countMaxLogin(@PathVariable String day) {
        int oldCount = 0;
        try {
            String ap = ResourceUtils.getFile("classpath:club/chillman/").getAbsolutePath();
            File file = new File(ap + File.separator + day + "_max.txt");
            if (file.exists()){
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                oldCount = Integer.parseInt(br.readLine().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ChillTeachException(20001,"文件处理异常");
        }
        return oldCount;
    }
    //查询当前在线人数
    @GetMapping("/countNowLogin")
    public int countNowLogin(){
        return loginCount.intValue();
    }


    @DeleteMapping("/logout")
    public R logout(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)&&loginCount.intValue()>0) loginCount.decrementAndGet();
        return R.ok().message("已注销登录");
    }


}

