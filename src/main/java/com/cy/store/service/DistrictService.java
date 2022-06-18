package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictService {
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
