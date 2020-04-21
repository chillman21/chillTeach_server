package club.chillman.staservice.service.impl;

import club.chillman.commonutils.R;
import club.chillman.staservice.client.UcenterClient;
import club.chillman.staservice.pojo.StatisticsDaily;
import club.chillman.staservice.mapper.StatisticsDailyMapper;
import club.chillman.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-19
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        QueryWrapper<StatisticsDaily> dayQueryWrapper0 = new QueryWrapper<>();
        dayQueryWrapper0.eq("date_calculated", day);
        StatisticsDaily oldSta = baseMapper.selectOne(dayQueryWrapper0);

        //远程调用得到某一天的注册人数
        R r = ucenterClient.countRegister(day);
        Integer registerNum = (Integer) r.getData().get("count");
        //把获取到的数据添加数据库
        Integer loginNum = ucenterClient.countMaxLogin(day);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(registerNum);

        daily.setDateCalculated(day);
        if (oldSta==null){
            daily.setLoginNum(loginNum);
            daily.setVideoViewNum(videoViewNum);
            daily.setCourseNum(courseNum);
            baseMapper.insert(daily);
        }else {
            daily.setLoginNum(oldSta.getLoginNum());
            daily.setVideoViewNum(oldSta.getVideoViewNum());
            daily.setCourseNum(oldSta.getCourseNum());
            baseMapper.update(daily,dayQueryWrapper0);
        }

    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);


        for (StatisticsDaily daily : staList) {
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
