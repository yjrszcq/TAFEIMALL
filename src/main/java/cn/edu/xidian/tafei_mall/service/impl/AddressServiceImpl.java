package cn.edu.xidian.tafei_mall.service.impl;

import cn.edu.xidian.tafei_mall.model.entity.Address;
import cn.edu.xidian.tafei_mall.mapper.AddressMapper;
import cn.edu.xidian.tafei_mall.model.entity.User;
import cn.edu.xidian.tafei_mall.model.vo.AddressUpdateVO;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.getAddressResponse;
import cn.edu.xidian.tafei_mall.service.AddressService;
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
        List<Address> addresses = addressMapper.selectList(new QueryWrapper<Address>().eq("user_id", user.getUserId()));
        if(addresses.isEmpty()){
            return null;
        }
        getAddressResponse Response= new getAddressResponse();
        for(Address address:addresses){
            Response.addAddress(address.getAddressId(),address.getAddress(),address.getCity(),address.getPostalCode());
        }
        return Response;
    }

    @Override
    public void updateAddress(AddressUpdateVO addressUpdateVO, String sessionId, String addressId){
        User user=userService.getUserInfo(sessionId);
        if(!addressMapper.selectById(addressId).getUserId().equals(user.getUserId())){
            throw new RuntimeException("没有权限修改该地址");
        }
        Address address=addressMapper.selectById(addressId);
        address.setAddress(addressUpdateVO.getAddress());
        address.setCity(addressUpdateVO.getCity());
        address.setPostalCode(addressUpdateVO.getPostalCode());
        addressMapper.updateById(address);
    }

    @Override
    public void deleteAddress(String addressId, String sessionId){
        User user=userService.getUserInfo(sessionId);
        addressMapper.deleteById(addressId);
    }
}
