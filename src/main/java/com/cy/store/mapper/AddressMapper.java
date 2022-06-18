package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * 插入用户的收货地址
     * @param address
     * @return
     */
    @Insert("""
            INSERT INTO t_address(aid, uid, name, province_name, province_code, city_name, city_code, area_name, area_code, zip, address, phone, tag, is_default, created_user, created_time, modified_user, modified_time)
            VALUES(#{aid}, #{uid}, #{name}, #{provinceName}, #{provinceCode}, #{cityName}, #{cityCode}, #{areaName}, #{areaCode}, #{zip}, #{address}, #{phone}, #{tag}, #{isDefault}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "aid")
//    @SelectKey(resultType = Integer.class, keyProperty = "aid", before = false, statement = {"SELECT @@Identity"})
    Integer insert(Address address);

    @ResultType(java.lang.Integer.class)
    @Select("""
            SELECT count(*) FROM t_address WHERE uid=#{uid}
            """)
    Integer countByUid(Integer uid);

    @Results(id = "addressResultMap", value = {
            @Result(property = "aid", column = "aid", id = true),
            @Result(property = "uid", column = "uid"),
            @Result(property = "name", column = "name"),
            @Result(property = "provinceName", column = "province_name"),
            @Result(property = "provinceCode", column = "province_code"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "cityCode", column = "city_code"),
            @Result(property = "areaName", column = "area_name"),
            @Result(property = "areaCode", column = "area_code"),
            @Result(property = "zip", column = "zip"),
            @Result(property = "address", column = "address"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "tag", column = "tag"),
            @Result(property = "isDefault", column = "is_default"),
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")
    })
    @Select("""
            SELECT * FROM t_address WHERE uid=#{uid} ORDER BY is_default DESC, created_time DESC
            """)
    List<Address> selectByUid(Integer uid);

    /**
     *
     * @param aid
     * @return
     */
    @ResultMap(value = "addressResultMap")
    @Select("""
            SELECT * FROM t_address WHERE aid=#{aid}
            """)
    Address selectByAid(Integer aid);

    @Update("""
            UPDATE t_address SET is_default=0 WHERE uid=#{uid}
            """)
    Integer updateNonDefault(Integer uid);

    @Update("""
            UPDATE t_address SET is_default=1, modified_user=#{modifiedUser}, modified_time=#{modifiedTime}
            WHERE aid=#{aid}
            """)
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     *
     * @param aid 收货地址id
     * @return 受影响行数
     */
    @Delete("""
            DELETE FROM t_address WHERE aid=#{aid}
            """)
    Integer deleteByAid(Integer aid);

    @ResultMap(value = "addressResultMap")
    @Select("""
            SELECT * FROM t_address WHERE uid=#{uid} ORDER BY modified_time DESC LIMIT 0,1
            """)
    Address selectLastModifiedByUid(Integer uid);
}
