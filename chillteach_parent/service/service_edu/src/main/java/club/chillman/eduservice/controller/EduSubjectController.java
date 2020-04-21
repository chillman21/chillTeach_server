package club.chillman.eduservice.controller;


import club.chillman.commonutils.R;
import club.chillman.eduservice.pojo.subject.OneSubject;
import club.chillman.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    //添加课程分类
    //获取上传过来的文件，把文件内容读出来
    @PostMapping("/import")
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类列表（树形）
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

