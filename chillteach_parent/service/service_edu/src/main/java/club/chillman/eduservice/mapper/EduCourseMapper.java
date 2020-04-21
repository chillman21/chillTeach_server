package club.chillman.eduservice.mapper;

import club.chillman.eduservice.pojo.EduCourse;
import club.chillman.eduservice.pojo.frontVo.CourseWebVo;
import club.chillman.eduservice.pojo.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getCoursePublishInfo(String courseId);

    CourseWebVo getAllCourseInfo(String courseId);
}
