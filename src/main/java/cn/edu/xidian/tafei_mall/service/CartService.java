package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Cart;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.CartItemAddVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Cart.CartResponse;
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

    CartResponse getCart(String userId);
    void addToCart(CartItemAddVO cartItemAddVO, User user);
}
