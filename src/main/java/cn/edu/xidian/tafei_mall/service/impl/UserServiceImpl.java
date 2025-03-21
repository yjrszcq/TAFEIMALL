package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.mapper.SessionMapper;
import cn.edu.xidian.tafei_mall.model.entity.Session;
import cn.edu.xidian.tafei_mall.model.vo.LoginRequestVO;
import cn.edu.xidian.tafei_mall.model.vo.UserRegistrationVO;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.mapper.UserMapper;
import cn.edu.xidian.tafei_mall.service.SessionService;
import cn.edu.xidian.tafei_mall.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionMapper sessionMapper;

    @Override
    public String login(LoginRequestVO loginRequestVO) {
        List<User> users = userMapper.selectByMap(BeanUtil.beanToMap(loginRequestVO));
        if(users.isEmpty()){
            return "Failed";
        }else{
            return sessionService.createSession(users.get(0))+","+users.get(0).getUserId();
        }
    }

    @Override
    public User register(UserRegistrationVO userRegistrationVO) {
        User user = BeanUtil.toBean(userRegistrationVO, User.class);
        user.setUserId(String.valueOf(UUID.randomUUID()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public boolean logout(String sessionId) {
        Session session = sessionMapper.selectOne(new QueryWrapper<Session>().like("session_token", sessionId));
        if(session != null){
            sessionMapper.delete(new QueryWrapper<Session>().like("session_token", sessionId));
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User getUserInfo(String sessionId) {
        Session session = sessionMapper.selectOne(new QueryWrapper<Session>().like("session_token", sessionId));
        if(session != null){
            return userMapper.selectOne(new QueryWrapper<User>().like("user_id", session.getUserId()));
        }else{
            return null;
        }
    }
}
