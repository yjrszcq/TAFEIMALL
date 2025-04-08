package cn.edu.xidian.tafei_mall.service;

import cn.edu.xidian.tafei_mall.model.entity.Address;
import cn.edu.xidian.tafei_mall.model.vo.AddressUpdateVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.xidian.tafei_mall.model.vo.Response.Address.getAddressResponse;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author shenyaoguan
 * @since 2025-03-17
 */
public interface AddressService extends IService<Address> {
    /**
     * 更新用户地址
     */
    void addAddress(AddressUpdateVO addressUpdateV0, String sessionId);

    getAddressResponse getAddress(String sessionId);

    void updateAddress(AddressUpdateVO addressUpdate, String sessionId, String addressId);

    void deleteAddress(String addressId, String sessionId);
}
