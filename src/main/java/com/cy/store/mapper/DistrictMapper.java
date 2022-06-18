package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DistrictMapper {
    /*@Results(value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "parent", column = "parent"),
            @Result(property = "code", column = "code"),
            @Result(property = "name", column = "name")
    })*/
    @ResultType(com.cy.store.entity.District.class)
    @Select("""
            SELECT * FROM t_dict_district WHERE parent=#{parent} ORDER BY code ASC
            """)
    List<District> selectByParent(String parent);

    @ResultType(java.lang.String.class)
    @Select("""
            SELECT name FROM t_dict_district WHERE code=#{code}
            """)
    String selectNameByCode(String code);
}
