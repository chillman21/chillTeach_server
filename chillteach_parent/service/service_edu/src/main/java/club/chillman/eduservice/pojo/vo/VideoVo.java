package club.chillman.eduservice.pojo.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class VideoVo implements Serializable {
    private String id;
    private String title;
    private Boolean isFree;
    private String videoSourceId;
}
