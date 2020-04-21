package club.chillman.eduservice.controller;


import club.chillman.commonutils.R;
import club.chillman.eduservice.pojo.EduChapter;
import club.chillman.eduservice.pojo.vo.ChapterVo;
import club.chillman.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;
    //课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("ChapterVideo",list);
    }
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        System.out.println(eduChapter);
        chapterService.save(eduChapter);
        return R.ok();
    }
    //根据id查询
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }
    //删除章节
    @DeleteMapping("/deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag  = chapterService.deleteChapter(chapterId);
        if (flag) return R.ok();
        else return R.error();
    }

}

