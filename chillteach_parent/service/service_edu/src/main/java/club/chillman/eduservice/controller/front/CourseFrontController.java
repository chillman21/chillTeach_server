package club.chillman.eduservice.controller.front;

import club.chillman.commonutils.JwtUtils;
import club.chillman.commonutils.R;
import club.chillman.commonutils.orderVo.CourseWebVoOrder;
import club.chillman.eduservice.client.OrdersClient;
import club.chillman.eduservice.pojo.EduCourse;
import club.chillman.eduservice.pojo.frontVo.CourseFrontVo;
import club.chillman.eduservice.pojo.frontVo.CourseWebVo;
import club.chillman.eduservice.pojo.vo.ChapterVo;
import club.chillman.eduservice.service.EduChapterService;
import club.chillman.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //条件查询带分页 查询课程
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable long page,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map =courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }
    //查询全部课程信息
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request){
        //自定义sql语句查询
        CourseWebVo courseWebVo= courseService.getAllCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        String token = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(token)) return R.ok().data("courseWebVo",courseWebVo)
                .data("chapterVideoList",chapterVideoList)
                .data("hasBoughtCourse",false).message("用户未登录");
        else{
            //根据课程id和用户id查询当前课程是否已经支付过
            boolean flag = ordersClient.hasBoughtCourse(courseId,token);

            return R.ok().data("courseWebVo",courseWebVo)
                    .data("chapterVideoList",chapterVideoList)
                    .data("hasBoughtCourse",flag);
        }
    }

    //根据课程id查询课程订单信息
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getAllCourseInfo(id);
        CourseWebVoOrder webVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,webVoOrder);
        return webVoOrder;
    }

}
