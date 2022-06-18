package com.cy.store.mapper;

import com.cy.store.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Results(id = "productResultMap",value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "item_type", property = "itemType"),
            @Result(column = "title", property = "title"),
            @Result(column = "sell_point", property = "sellPoint"),

            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")
    })
    @Select("""
            SELECT * FROM t_product ORDER BY priority DESC LIMIT 0,4
            """)
    List<Product> listBestSales();

    @ResultMap(value = "productResultMap")
    @Select("""
            SELECT * FROM t_product WHERE id=#{id}
            """)
    Product selectById(Integer id);
}
