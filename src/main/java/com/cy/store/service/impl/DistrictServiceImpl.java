package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.selectByParent(parent);
        /**
         * 避免无效数据传输
         */
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }

        return list;
    }

    @Override
    public String findNameByCode(String code) {
        return districtMapper.selectNameByCode(code);
    }
}
