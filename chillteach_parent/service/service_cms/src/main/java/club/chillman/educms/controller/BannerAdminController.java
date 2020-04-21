package club.chillman.educms.controller;

import club.chillman.commonutils.R;
import club.chillman.educms.pojo.CrmBanner;
import club.chillman.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/educms/banner/admin")
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return R.ok()
                .data("items",pageBanner.getRecords())
                .data("total",pageBanner.getTotal());
    }

    @ApiOperation(value = "添加Banner")
    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        bannerService.saveBanner(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/updateBanner")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateBannerById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("removeBanner/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeBannerById(id);
        return R.ok();
    }
    @ApiOperation(value = "获取Banner")
    @GetMapping("getBanner/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }
}

