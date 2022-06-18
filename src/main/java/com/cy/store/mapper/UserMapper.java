package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 如果找到则返回该用户数据，否则返回null
     */
    @Results(id = "userResultMap", value = {
            @Result(property = "uid", column = "uid", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "email", column = "email"),
            @Result(property = "gender", column = "gender"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "isDelete", column = "is_delete")
    })
    @Select("SELECT * FROM t_user WHERE username=#{username}")
    User selectByUsername(String username);

    /**
     *
     * @param uid 用户id
     * @return 用户
     */
    @ResultMap(value = "userResultMap")
    @Select("SELECT * FROM t_user WHERE uid=#{uid}")
    User selectByUid(Integer uid);

    /**
     * 插入用户的数据
     * @param user 用户数据
     * @return 受影响的行数（增、删、改都受影响的行数作为返回值，可以据此判断是否执行成功）
     */
    // @ResultMap("userResultMap")
    @Insert({
            "INSERT INTO t_user(username, password, salt, phone, email, gender, avatar, is_delete)",
            "VALUES(#{username}, #{password}, #{salt}, #{phone}, #{email}, #{gender}, #{avatar}, #{isDelete})"
    })
    // useGeneratedKeys属性：表示主键开启递增
    // keyProperty属性：表示哪个字段作为自增主键
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    Integer insert(User user);


    /**
     *
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    @Update({
            "UPDATE t_user",
            "SET password=#{password}, modified_user=#{modifiedUser}, modified_time=#{modifiedTime}",
            "WHERE uid=#{uid}"
    })
    Integer updatePasswordByUid(
            Integer uid,
            String password,
            String modifiedUser,
            Date modifiedTime);

    /**
     *
     * @param user
     * @return
     */
    @Update("""
            <script>
            UPDATE t_user
            SET
            <if test="phone!=null">phone=#{phone},</if>
            <if test="email!=null">email=#{email},</if>
            <if test="gender!=null">gender=#{gender},</if>
            modified_user=#{modifiedUser},
            modified_time=#{modifiedTime}
            WHERE uid=#{uid}
            </script>
            """)
    Integer updateInfoByUid(User user);

    @Update("""
            UPDATE t_user
            SET avatar=#{avatar}, modified_user=#{modifiedUser}, modified_time=#{modifiedTime}
            WHERE uid=#{uid}
            """)
    Integer updateAvatarByUid(
            @Param("uid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);
}
