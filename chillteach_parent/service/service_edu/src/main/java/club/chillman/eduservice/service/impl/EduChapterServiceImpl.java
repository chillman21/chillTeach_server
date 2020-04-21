package club.chillman.eduservice.service.impl;

import club.chillman.eduservice.pojo.EduChapter;
import club.chillman.eduservice.mapper.EduChapterMapper;
import club.chillman.eduservice.pojo.EduVideo;
import club.chillman.eduservice.pojo.vo.ChapterVo;
import club.chillman.eduservice.pojo.vo.VideoVo;
import club.chillman.eduservice.service.EduChapterService;
import club.chillman.eduservice.service.EduVideoService;
import club.chillman.servicebase.exception.ChillTeachException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.orderByAsc("sort");
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);


        List<ChapterVo> finalList = new ArrayList<>();
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);

            QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
            wrapperChapter.orderByAsc("sort");
            wrapperVideo.eq("chapter_id",eduChapter.getId());
            List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
            List<VideoVo> children = new ArrayList<>();
            for (EduVideo eduVideo : eduVideoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                children.add(videoVo);
            }
            chapterVo.setChildren(children);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(queryWrapper);
        if (count>0) throw new ChillTeachException(20001,"该章节还存在子章节，不能删除！");
        else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
