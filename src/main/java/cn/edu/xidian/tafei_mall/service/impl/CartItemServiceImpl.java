package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.CartItem;
import cn.edu.xidian.tafei_mall.mapper.CartItemMapper;
import cn.edu.xidian.tafei_mall.model.vo.CartItemUpdateVO;
import cn.edu.xidian.tafei_mall.service.CartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车项表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {
    @Override
    public void updateCartItem(String itemId, CartItemUpdateVO cartItemUpdateVO) {
        CartItem cartItem = getById(itemId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItemUpdateVO.getQuantity());
            updateById(cartItem);
        }else {
            throw new RuntimeException("购物车项不存在");
        }
    }

    @Override
    public void deleteCartItem(String itemId) {
        CartItem cartItem = getById(itemId);
        if (cartItem != null) {
            removeById(itemId);
        }else {
            throw new RuntimeException("购物车项不存在");
        }
    }
}
