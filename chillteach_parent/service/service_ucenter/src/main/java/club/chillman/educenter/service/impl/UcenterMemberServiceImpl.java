package club.chillman.educenter.service.impl;



import club.chillman.commonutils.MD5;
import club.chillman.educenter.pojo.UcenterMember;
import club.chillman.educenter.mapper.UcenterMemberMapper;
import club.chillman.educenter.pojo.vo.RegisterVo;
import club.chillman.educenter.service.UcenterMemberService;
import club.chillman.educenter.utils.JwtUtils;
import club.chillman.servicebase.exception.ChillTeachException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author niu
 * @since 2020-04-16
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public String login(UcenterMember member) {
        //获取登录的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码为空的判断
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new ChillTeachException(20001,"登录失败,手机号和密码不能为空！");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查出来的对象是否为空
        if (mobileMember==null){//没有这个手机号
            throw new ChillTeachException(20001,"登录失败,该账户不存在！");
        }
        //判断密码 用md5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new ChillTeachException(20001,"登录失败,账号或密码错误！");
        }
        //判断该用户是否被禁用
        if (mobileMember.getIsDisabled()){
            throw new ChillTeachException(20001,"登录失败,您的账户已封停！");
        }
        //登录成功
        //jwt生成token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());


        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码
        //手机号和密码为空的判断
        if (StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)
        ||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new ChillTeachException(20001,"注册失败");
        }
        //判断验证码是否正确
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new ChillTeachException(20001,"注册失败,验证码错误！");
        }
        //判断手机号是否重复，表里是存在相同的手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0) throw new ChillTeachException(20001,"注册失败,该手机号码已存在！");
        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要md5加密
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://ftp.bmp.ovh/imgs/2020/04/40f909490c5267e2.png");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
