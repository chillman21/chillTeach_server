package club.chillman.eduservice.controller;


import club.chillman.commonutils.R;
import club.chillman.eduservice.client.VodClient;
import club.chillman.eduservice.pojo.EduVideo;
import club.chillman.eduservice.service.EduVideoService;
import club.chillman.servicebase.exception.ChillTeachException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author niu
 * @since 2020-04-12
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //添加
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }
    //删除小节  删除小节时同时要删除视频
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        //根据小节id获取到视频id
        EduVideo eduVideo = videoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeVideo(videoSourceId);
            if (result.getCode()==20001){
                throw new ChillTeachException(20001,"删除视频失败!触发熔断器");
            }
        }
        videoService.removeById(videoId);
        return R.ok();
    }
    //根据id查询
    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }
    //修改
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

