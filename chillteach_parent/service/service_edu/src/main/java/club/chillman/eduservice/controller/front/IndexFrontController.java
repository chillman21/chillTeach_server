package club.chillman.eduservice.controller.front;

import club.chillman.commonutils.R;
import club.chillman.eduservice.pojo.EduCourse;
import club.chillman.eduservice.pojo.EduTeacher;
import club.chillman.eduservice.service.EduCourseService;
import club.chillman.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

    
    //查询前8条热门课程，查询前4条名师
    @GetMapping
    public R index(){
        List<EduCourse> courseList = courseService.hotCourse();
        List<EduTeacher> teacherList = teacherService.hotTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
