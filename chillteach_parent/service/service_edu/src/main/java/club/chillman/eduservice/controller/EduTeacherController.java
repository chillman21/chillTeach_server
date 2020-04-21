package club.chillman.eduservice.controller;


import club.chillman.commonutils.R;
import club.chillman.eduservice.pojo.EduTeacher;
import club.chillman.eduservice.pojo.vo.TeacherQuery;
import club.chillman.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-10
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    //把Service注入
    @Autowired
    public EduTeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/findAll")
    public R findAll(){
        //调用service方法实现查询所有的操作
        List<EduTeacher> lists = teacherService.list(null);
        return R.ok().data("items",lists);
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeTeacher(@ApiParam(name = "id",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag)return R.ok();
        else return R.error();
    }


    @ApiOperation("分页查询讲师")
    @GetMapping("/pageListTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit){
        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current,limit);
//        try {
//            int i = 1/0;
//        } catch (Exception e) {
//            throw new ChillTeachException(20001,"执行了自定义异常处理..");
//        }
        //调用方法实现分页,
        //调用方法时，底层封装，把分页所有数据都封装到Page对象里面
        teacherService.page(teacherPage,null);
        long total = teacherPage.getTotal();//总记录数
        List<EduTeacher> records = teacherPage.getRecords();//当前页数据List集合
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("分页带条件查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageConditionTeacher( @ApiParam(name = "current", value = "当前页码", required = true)
                                           @PathVariable("current") Long current,

                                   @ApiParam(name = "limit", value = "每页记录数", required = true)
                                       @PathVariable("limit") Long limit,

                                   @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                                   @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> pageParam = new Page<EduTeacher>(current,limit);
        //System.out.println("******"+current+" "+limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        //System.out.println("----------"+records);

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.save(eduTeacher);
        if (flag) return R.ok();
        else return R.error();
    }

    @ApiOperation("查找讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    @ApiOperation("讲师修改")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher ){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) return R.ok();
        else return R.error();
    }








}

