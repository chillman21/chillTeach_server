package club.chillman.educms.service.impl;

import club.chillman.educms.pojo.CrmBanner;
import club.chillman.educms.mapper.CrmBannerMapper;
import club.chillman.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(key = "'selectIndexList'",value = "banner")
    public List<CrmBanner> getAllBanner() {
        //根据id降序排列，显示前两条
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @Override
    @CacheEvict(value = "banner", allEntries=true)
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
