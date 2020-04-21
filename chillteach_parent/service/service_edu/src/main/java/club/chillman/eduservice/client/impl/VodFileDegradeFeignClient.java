package club.chillman.eduservice.client.impl;

import club.chillman.commonutils.R;
import club.chillman.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //以下方法出错后才会执行
    @Override
    public R removeVideo(String id) {
        return R.error().message("删除视频出错惹");
    }

    @Override
    public R removeBatchVideo(List<String> videoIdList) {
        return R.error().message("批量删除视频出错惹");
    }
}
