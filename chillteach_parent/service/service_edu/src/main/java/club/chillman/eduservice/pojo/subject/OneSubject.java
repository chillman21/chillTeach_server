package club.chillman.eduservice.pojo.subject;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//一级分类
@Data
public class OneSubject implements Serializable {
    private String id;
    private String title;
    private List<TwoSubject> children = new ArrayList<>();
}
