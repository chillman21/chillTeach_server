package club.chillman.eduservice.controller;


import club.chillman.commonutils.R;
import club.chillman.eduservice.pojo.EduCourse;
import club.chillman.eduservice.pojo.vo.CourseInfoVo;
import club.chillman.eduservice.pojo.vo.CoursePublishVo;
import club.chillman.eduservice.pojo.vo.CourseQuery;
import club.chillman.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }
    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }
    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return  R.ok();
    }
    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishVo =courseService.getCoursePublishInfo(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }
    //课程最终发布
    @PutMapping("/publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @ApiOperation("分页带条件查询课程")
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageConditionCourse( @ApiParam(name = "current", value = "当前页码", required = true)
                                   @PathVariable("current") Long current,

                                   @ApiParam(name = "limit", value = "每页记录数", required = true)
                                   @PathVariable("limit") Long limit,

                                   @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                                   @RequestBody(required = false) CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<EduCourse>(current,limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }
    @ApiOperation("删除课程")
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

