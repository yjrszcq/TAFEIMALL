package cn.edu.xidian.tafei_mall.mapper;

import cn.edu.xidian.tafei_mall.model.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

//    @Override
//    @Insert("INSERT INTO `order` (order_id, user_id, total_amount, payment_method, shipping_address_id) " +
//            "VALUES (#{orderId}, #{userId}, #{totalAmount}, #{paymentMethod}, #{shippingAddressId})")
//    int insert(Order order);
//
//    @Override
//    @Update("UPDATE `order` SET user_id = #{userId}, total_amount = #{totalAmount}, " +
//            "payment_method = #{paymentMethod}, shipping_address_id = #{shippingAddressId}, status = #{status}" +
//            " WHERE order_id = #{orderId}")
//    int updateById(Order order);
//
//    @Select("SELECT * FROM `order` WHERE user_id = #{userId}")
//    List<Order> findByUserId(@Param("userId") String userId);
//
//    @Delete("DELETE FROM `order` WHERE order_id = #{orderId}")
//    int deleteById(@Param("orderId") String orderId);
//
//
//    @Select("SELECT * FROM `order` WHERE order_id = #{orderId}")
//    Order selectById(@Param("orderId") String orderId);
}
