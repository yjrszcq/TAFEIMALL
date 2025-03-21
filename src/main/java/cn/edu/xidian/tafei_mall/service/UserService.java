package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.LoginRequestVO;
import cn.edu.xidian.tafei_mall.model.vo.UserRegistrationVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     */
    String login(LoginRequestVO loginRequestVO);

    /**
     * 注册
     */
    User register(UserRegistrationVO userRegistrationVO);

    /**
     * 登出
     */
    boolean logout(String sessionId);
}
