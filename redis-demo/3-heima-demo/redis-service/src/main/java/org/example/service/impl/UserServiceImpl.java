package org.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.LoginFormDTO;
import org.example.dto.Result;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.IUserService;
import org.example.utils.RedisConstants;
import org.example.utils.RegexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.example.utils.RedisConstants.*;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取手机验证码
     * @param phone
     * @return
     */
    @Override
    public Result sendCode(String phone) {
        // 1. 校验手机号
        if (!RegexUtils.isCodeInvalid(phone)) {
            // 1.1 手机号不符合
            return Result.fail("手机号格式错误！");
        }
        // 2. 手机号符合，生成验证码，使用 cn.hutool 依赖的工具类
        String code = RandomUtil.randomNumbers(6);
        // 保存手机号和验证码到 redis
        // 有效期 2 分钟
        // RedisConstants 类，定义常量
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,code,
                LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 发送验证码
        // 调用第三方短信服务（略）
        log.debug("{} 的短信验证码为：{}",phone,code);
        return Result.ok("验证码发送成功！");
    }

    /**
     * 登录
     * @param loginFormDTO
     * @return
     */
    @Override
    public Result login(LoginFormDTO loginFormDTO) {
        log.info("登录！");
        String phone = loginFormDTO.getPhone();
        String code = loginFormDTO.getCode();
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone);
        // 1. 校验手机号和验证码
        if (!RegexUtils.isCodeInvalid(phone)) {
            // 1.1 手机号不符合
            return Result.fail("手机号格式错误！");
        }
        // 2. 校验验证码
        if (!code.equals(cacheCode)){
            // 2.1 验证码不正确
            return Result.fail("验证码错误！");
        }
        // 3. 通过手机号查询用户
        User user = query().eq("phone", phone).one();
        // 4. 判断用户是否存在
        if (user == null){
            // 4.1 不存在，注册
            // 自定义一个通过手机号创建用户的方法
            user = createUserWithPhone(phone);
        }
        // 5. 保存用户信息到 redis，使用 UserDto ，避免返回过多信息
        // 5.1 随机生成一个 token ，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 5.2 将 User 对象转换为 HashMap 存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(userDTO);
        log.info("stringObjectMap:{}",stringObjectMap);
        String tokenKey = LOGIN_USER_KEY + token;
        log.info("tokenKey_1:{}",tokenKey);
        // Map 中的值要为 String 类型
//        stringRedisTemplate.opsForHash().put(tokenKey,"id",stringObjectMap.get("id").toString());
//        stringRedisTemplate.opsForHash().put(tokenKey,"nickName",stringObjectMap.get("nickName"));
//        stringRedisTemplate.opsForHash().put(tokenKey,"icon",stringObjectMap.get("icon"));
        // stringRedisTemplate.opsForHash().putAll(tokenKey,stringObjectMap);

        // 或
        // 将 Map 的 Value 转换为 String 类型
        Map<String, String> valueMap = new HashMap<>();
        stringObjectMap.forEach((key, value) -> valueMap.put(key, value.toString()));
        stringRedisTemplate.opsForHash().putAll(tokenKey, valueMap);

        log.info("tokenKey_2:{}",tokenKey);
        // 5.3 设置 token 有效期
        stringRedisTemplate.expire(tokenKey,LOGIN_USER_TTL,TimeUnit.MINUTES);
        // 6. 返回 token
        return Result.ok(token);
    }

    /**
     * 更加手机号创建用户
     * @param phone
     * @return
     */
    private User createUserWithPhone(String phone) {
        // 1. 创建用户
        User user = new User();
        user.setPhone(phone);
        user.setNickName("用户"+RandomUtil.randomNumbers(10));
        // 2. 保存用户
        save(user);
        return user;
    }
}
