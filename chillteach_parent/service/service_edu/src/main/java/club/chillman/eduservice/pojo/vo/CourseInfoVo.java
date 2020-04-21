package club.chillman.eduservice.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "课程基本信息", description = "编辑课程基本信息的表单对象")
public class CourseInfoVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID",example = "1192252213659774977")
    private String id;

    @ApiModelProperty(value = "课程讲师ID",example = "1189389726308478977")
    private String teacherId;

    @ApiModelProperty(value = "课程二级分类ID",example = "5")
    private String subjectId;

    @ApiModelProperty(value = "课程一级分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看",example = "0")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时",example = "64")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;
}
