package club.chillman.eduservice.service;

import club.chillman.eduservice.pojo.EduCourse;
import club.chillman.eduservice.pojo.frontVo.CourseFrontVo;
import club.chillman.eduservice.pojo.frontVo.CourseWebVo;
import club.chillman.eduservice.pojo.vo.CourseInfoVo;
import club.chillman.eduservice.pojo.vo.CoursePublishVo;
import club.chillman.eduservice.pojo.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishInfo(String courseId);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourse(String courseId);

    List<EduCourse> hotCourse();

    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getAllCourseInfo(String courseId);
}
