package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Cart;
import cn.edu.xidian.tafei_mall.mapper.CartMapper;
import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import cn.edu.xidian.tafei_mall.model.vo.CartItemAddVO;
import cn.edu.xidian.tafei_mall.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Override
    public boolean validateSession(String sessionId) {
        return sessionId != null &&!sessionId.isBlank();
    }

    @Override
    public Cart getOrCreateCart(String userId) {
        Cart cart = lambdaQuery()
                .eq(Cart::getUserId, userId)
                .one();
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setCreatedAt(LocalDateTime.now());
            cart.setUpdatedAt(LocalDateTime.now());
            save(cart);
        }
        return cart;
    }

    @Override
    public CartItem createCartItem(String cartId, CartItemAddVO vo) {
        CartItem item = new CartItem();
        item.setCartId(cartId);
        item.setProductId(vo.getProductId());
        item.setQuantity(vo.getQuantity());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return item;
    }
}
