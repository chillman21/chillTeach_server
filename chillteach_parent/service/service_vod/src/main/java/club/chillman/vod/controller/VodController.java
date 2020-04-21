package club.chillman.vod.controller;

import club.chillman.commonutils.R;
import club.chillman.servicebase.exception.ChillTeachException;
import club.chillman.vod.service.VodService;
import club.chillman.vod.utils.ConstantVodUtil;
import club.chillman.vod.utils.InitVodClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @ApiOperation("上传视频到阿里云")
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId=vodService.uploadVideo(file);
        if(videoId!=null) return R.ok().data("videoId",videoId).message("视频上传成功");
        else return R.error().message("上传失败");
    }
    //根据视频id删除阿里云视频
    @DeleteMapping("/removeVideo/{id}")
    public R removeVideo(@PathVariable String id){

        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request =new DeleteVideoRequest();
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ChillTeachException(20001,"删除视频失败");
        }

    }
    //删除多个阿里云视频的方法
    @DeleteMapping("/removeBatchVideo")
    public R removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeBatchVideo(videoIdList);
        return R.ok().message("批量删除视频成功");
    }
    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){

        try {
            DefaultAcsClient client = InitVodClient
                    .initVodClient(ConstantVodUtil.ACCESS_KEY_ID,
                            ConstantVodUtil.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request设置视频id
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
            //调用方法得到凭证
        } catch (Exception e) {
            throw new ChillTeachException(20001,"获取凭证失败");
        }
    }

}
