package club.chillman.eduservice.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="EduTeacher对象", description="讲师")
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID",example = "11")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师简介",example = "很强")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师",example = "老金牌讲师了")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师",example = "1")
    private Integer level;

    @ApiModelProperty(value = "讲师头像",example = "https://online-teach-file.oss-cn-beijing.aliyuncs.com/teacher/2019/10/30/b8aa36a2-db50-4eca-a6e3-cc6e608355e0.png")
    private String avatar;

    @ApiModelProperty(value = "排序",example = "0")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除",example = "false",hidden = true)
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间",hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
