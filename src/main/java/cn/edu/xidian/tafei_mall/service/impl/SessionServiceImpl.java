package cn.edu.xidian.tafei_mall.service.impl;


import cn.edu.xidian.tafei_mall.model.entity.Session;
import cn.edu.xidian.tafei_mall.mapper.SessionMapper;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.service.SessionService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.UUID;

/**
 * <p>
 * 会话表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session> implements SessionService {
    @Autowired
    private SessionMapper sessionMapper;

    @Override
    public String createSession(User user) {
        Session session=BeanUtil.toBean(user, Session.class);
        session.setSessionId(String.valueOf(UUID.randomUUID()));
        session.setSessionToken(String.valueOf(UUID.randomUUID()));
        session.setUserId(user.getUserId());
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusHours(2));
        sessionMapper.insert(session);

        return session.getSessionToken();
    }

}
