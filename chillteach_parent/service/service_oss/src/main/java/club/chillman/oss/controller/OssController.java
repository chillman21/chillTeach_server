package club.chillman.oss.controller;

import club.chillman.commonutils.R;
import club.chillman.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/file")
public class OssController {
    @Autowired
    private OssService ossService;
    //上传头像的方法
    @ApiOperation(value = "头像上传")
    @PostMapping("/upload")
    public R uploadOss(MultipartFile file){
        //获取上传的文件
        //返回oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
