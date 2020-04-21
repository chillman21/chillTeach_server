package club.chillman.educms.controller;

import club.chillman.commonutils.R;
import club.chillman.educms.pojo.CrmBanner;
import club.chillman.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台banner显示
 */
@RestController
@RequestMapping("/educms/banner/font")
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;
    //查询所有banner
    @GetMapping("/getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list = bannerService.getAllBanner();
        return R.ok().data("list",list);
    }
}

