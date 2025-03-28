package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Address;
import cn.edu.xidian.tafei_mall.mapper.AddressMapper;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.AddressUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.getAddressResponse;
import cn.edu.xidian.tafei_mall.service.AddressService;
import cn.edu.xidian.tafei_mall.service.UserService;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserService userService;

    @Override
    public void addAddress(AddressUpdateVO addressUpdateVO, String sessionId){
        Address address = BeanUtil.toBean(addressUpdateVO, Address.class);
        User user=userService.getUserInfo(sessionId);
        address.setUserId(user.getUserId());
        address.setAddressId(String.valueOf(UUID.randomUUID()));
        addressMapper.insert(address);
    }

    @Override
    public getAddressResponse getAddress(String sessionId){
        User user=userService.getUserInfo(sessionId);
        Address address=addressMapper.selectById(user.getUserId());
        getAddressResponse getAddressResponse=new getAddressResponse();
        BeanUtil.copyProperties(address,getAddressResponse);
        return getAddressResponse;
    }

    @Override
    public void updateAddress(AddressUpdateVO addressUpdateVO, String sessionId){
        User user=userService.getUserInfo(sessionId);
        Address address=addressMapper.selectById(user.getUserId());
        BeanUtil.copyProperties(addressUpdateVO,address);
        addressMapper.updateById(address);
    }

    @Override
    public void deleteAddress(Integer addressId, String sessionId){
        User user=userService.getUserInfo(sessionId);
        addressMapper.deleteById(user.getUserId());
    }
}
