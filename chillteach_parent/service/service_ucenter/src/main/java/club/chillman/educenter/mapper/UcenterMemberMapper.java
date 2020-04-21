package club.chillman.educenter.mapper;

import club.chillman.educenter.pojo.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author niu
 * @since 2020-04-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer countRegisterDay(@Param("day") String day);
}
