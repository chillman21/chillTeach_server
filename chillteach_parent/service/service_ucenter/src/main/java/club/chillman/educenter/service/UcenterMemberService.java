package club.chillman.educenter.service;

import club.chillman.educenter.pojo.UcenterMember;
import club.chillman.educenter.pojo.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author niu
 * @since 2020-04-16
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer countRegisterDay(String day);
}
