package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.AddressService;
import com.cy.store.service.DistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    // 添加用户的收货地址的业务依赖于DistrictService的业务层接口
    @Autowired
    private DistrictService districtService;

    @Value("${user.address.max-count}") // 20
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);

        if (count >= maxCount) {
            throw new AddressCountLimitException("收货地址超出上限");
        }

        address.setProvinceName(districtService.findNameByCode(address.getProvinceCode()));
        address.setCityName(districtService.findNameByCode(address.getCityCode()));
        address.setAreaName(districtService.findNameByCode(address.getAreaCode()));

        address.setUid(uid);
        address.setIsDefault(count == 0 ? 1 : 0); // 初次插入的地址为默认地址
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);

        if (rows != 1) {
            throw new InsertException("插入用户地址产生未知异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.selectByUid(uid);

        for (Address address : list) {
//            address.setAid(null);
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setZip(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }

        return list;
    }

    @Override
    public void setDefaultAddress(Integer aid, Integer uid, String username) {
        Address address = addressMapper.selectByAid(aid);

        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }

        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }

        Integer rows = addressMapper.updateNonDefault(uid);

        if (rows < 1) {
            throw new UpdateException("更新数据产生未知异常");
        }

        rows = addressMapper.updateDefaultByAid(aid, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    public void removeAddress(Integer aid, Integer uid, String username) {
        Address result = addressMapper.selectByAid(aid);

        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }

        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        Integer rows = addressMapper.deleteByAid(aid);

        if (rows != 1) {
            throw new DeleteException("删除数据产生未知的异常");
        }

        if (addressMapper.countByUid(uid) == 0) {
            return;
        }

        if (result.getIsDefault() == 1) {
            Address newDefaultAddress = addressMapper.selectLastModifiedByUid(uid);
            rows = addressMapper.updateDefaultByAid(newDefaultAddress.getAid(), username, new Date());

            if (rows != 1) {
                throw new UpdateException("更新数据时产生未知异常");
            }
        }
    }

    @Override
    public Address getByAidAndUid(Integer aid, Integer uid) {
        Address address = addressMapper.selectByAid(aid);

        if (address == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }

        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);

        return address;
    }
}
