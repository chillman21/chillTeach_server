package club.chillman.eduservice.service.impl;

import club.chillman.eduservice.client.VodClient;
import club.chillman.eduservice.pojo.EduVideo;
import club.chillman.eduservice.mapper.EduVideoMapper;
import club.chillman.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id 查询课程所有的视频id
        QueryWrapper<EduVideo> queryWrapperVideo = new QueryWrapper<>();
        queryWrapperVideo.eq("course_id",courseId);
        queryWrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapperVideo);
        //  List<EduVideo>转List<String>
        List<String> videoIdList = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {

            String videoSourceId = eduVideo.getVideoSourceId();
            System.out.println(videoSourceId);
            if (!StringUtils.isEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        if (videoIdList.size()>0){
            vodClient.removeBatchVideo(videoIdList);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        baseMapper.delete(queryWrapper);
    }
}
