package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {
    @Insert("""
            INSERT INTO t_cart (uid, pid, price, num, created_user, created_time, modified_user, modified_time)
            VALUES (#{uid}, #{pid}, #{price}, #{num}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "cid")
    Integer insert(Cart cart);

    @Update("""
            UPDATE t_cart SET num=#{num}, modified_user=#{modifiedUser}, modified_time=#{modifiedTime}
            WHERE cid=#{cid}
            """)
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根據用戶的id和商品的id查詢購物車中的數據
     * @param uid 用戶id
     * @param pid 商品id
     */
    @Results(id = "cartMapperResultMap", value = {
            @Result(property = "cid", column = "cid", id = true),
            @Result(property = "uid", column = "uid"),
            @Result(property = "pid", column = "pid"),
            @Result(property = "price", column = "price"),
            @Result(property = "num", column = "num"),
            @Result(property = "createdUser", column = "created_user"),
            @Result(property = "createdTime", column = "created_time"),
            @Result(property = "modifiedUser", column = "modified_user"),
            @Result(property = "modifiedTime", column = "modified_time")
    })
    @Select("""
            SELECT * FROM t_cart WHERE uid=#{uid} AND pid=#{pid}
            """)
    Cart selectByUidAndPid(Integer uid, Integer pid);

    @ResultType(value = com.cy.store.vo.CartVO.class)
    @Select("""
            SELECT
                cid,
                uid,
                pid,
                t_cart.price,
                t_cart.num,
                t_product.title,
                t_product.image,
                t_product.price AS realPrice
            FROM
                t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
            WHERE
                uid=#{uid}
            ORDER BY
                t_cart.created_time DESC
            """)
    List<CartVO> selectVOByUid(Integer uid);

    @ResultMap("cartMapperResultMap")
    @Select("""
            SELECT * FROM t_cart WHERE cid=#{cid}
            """)
    Cart selectByCid(Integer cid);

    @ResultType(value = com.cy.store.vo.CartVO.class)
    @Select("""
            <script>
                SELECT
                    cid,
                    uid,
                    pid,
                    t_cart.price,
                    t_cart.num,
                    t_product.title,
                    t_product.image,
                    t_product.price AS realPrice
                FROM
                    t_cart LEFT JOIN t_product ON t_cart.pid=t_product.id
                WHERE
                    cid IN (
                        <foreach collection="array" item="cid" separator=",">
                            #{cid}
                        </foreach>
                    )
                ORDER BY
                    t_cart.created_time DESC
            </script>
            """)
    List<CartVO> selectVOByCids(Integer[] cids);
}
