package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Cart;
import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import cn.edu.xidian.tafei_mall.model.vo.CartItemAddVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface CartService extends IService<Cart> {
    /**
     * 验证Session-Id
     */
    boolean validateSession(String sessionId);
    /**
     * 获取或创建购物车
     */
    Cart getOrCreateCart(String userId);
    /**
     * 创建购物车商品项
     */
    CartItem createCartItem(String cartId, CartItemAddVO vo);
}
