package club.chillman.eduservice.service;

import club.chillman.eduservice.pojo.EduSubject;
import club.chillman.eduservice.pojo.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);
    //课程分类列表（树形结构）
    List<OneSubject> getAllOneTwoSubject();
}
