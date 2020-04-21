package club.chillman.eduservice.client;

import club.chillman.commonutils.R;
import club.chillman.eduservice.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
//要调用的服务名称
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {
    @DeleteMapping("/eduvod/video/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id); //PathVariable一定要指定参数名称

    @DeleteMapping("/eduvod/video/removeBatchVideo")
    public R removeBatchVideo(@RequestParam("videoIdList") List<String> videoIdList);
}
