package club.chillman.eduservice.service.impl;

import club.chillman.eduservice.listener.SubjectExcelListener;
import club.chillman.eduservice.pojo.EduSubject;
import club.chillman.eduservice.mapper.EduSubjectMapper;
import club.chillman.eduservice.pojo.excel.SubjectData;
import club.chillman.eduservice.pojo.subject.OneSubject;
import club.chillman.eduservice.pojo.subject.TwoSubject;
import club.chillman.eduservice.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();

            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //课程分类列表（树形结构）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //所有一级分类：
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        //所有二级分类：
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        oneWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        for (EduSubject eduSubject1 : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject1,oneSubject);
            finalSubjectList.add(oneSubject);
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            for (EduSubject eduSubject2 : twoSubjectList) {
                //判断二级分类的parent_id和一级分类的id是否相等
                if (eduSubject2.getParentId().equals(eduSubject1.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);

        }


        return finalSubjectList;
    }
}
