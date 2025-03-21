package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.vo.UserRegistrationVO;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.mapper.UserMapper;
import cn.edu.xidian.tafei_mall.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(User user) {
        return null;
    }

    @Override
    public User register(UserRegistrationVO userRegistrationVO) {
        User user = BeanUtil.toBean(userRegistrationVO, User.class);
        user.setUserId(String.valueOf(UUID.randomUUID()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public boolean logout(User user) {
        return false;
    }
}
