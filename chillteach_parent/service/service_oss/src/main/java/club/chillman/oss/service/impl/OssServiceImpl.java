package club.chillman.oss.service.impl;

import club.chillman.oss.service.OssService;
import club.chillman.oss.utils.ConstantPropertiesUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
public class OssServiceImpl  implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketname = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;

        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","")+"-";
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath+"/"+uuid+filename;
            //第一个参数，bucket名称
            //第二个参数 上传到oss文件路径和文件名称
            ossClient.putObject(bucketname, filename, inputStream);
            //把上传之后的文件路径返回
            String url = "https://"+bucketname+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭OSSClient。
            assert ossClient != null;
            ossClient.shutdown();
        }

    }
}
