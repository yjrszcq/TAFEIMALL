package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Session;
import cn.edu.xidian.tafei_mall.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会话表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface SessionService extends IService<Session> {
    /**
     * 创建会话
     */
    String createSession(User user);
}
