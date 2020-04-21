package club.chillman.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import club.chillman.aclservice.entity.Permission;
import club.chillman.aclservice.service.IndexService;
import club.chillman.aclservice.service.PermissionService;
import club.chillman.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/info")
    public R info(){
        //获取当前登录用户用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("*****"+authentication);
        String username = authentication.getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/menu")
    public R getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }

}
