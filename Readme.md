# Spring Boot + MyBatis + MySQL 电脑商城项目实战
## 项目分析
### 项目功能
登录、注册、热销商品、用户管理（密码、个人信息、头像、收货地址）、购物车（展示、增加、删除）、订单
## 总体开发顺序
注册、登录、用户管理、购物车、商品、订单
### 单一模块开发顺序
* 持久层开发：依据前端页面的设置规划相关的SQL语句，并进行配置
* 业务层开发：核心功能控制、业务操作以及异常处理
* 控制层开发：接受请求、处理相应
* 前端开发*：js、jQuery、ajax
## 项目环境
* JDK：1.8
* maven：3.6.1（本项目3.8.1）
* 数据库：MySQL（5.1及以上）
* 开发平台：Idea
## 搭建项目
1. 项目名称及结构：com.cy.store 
2. 资源文件：resources文件夹下 
3. 单元测试：test.com.cy.store 
4. properties配置文件
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/store?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=123456
```
5. 创建store数据库 
6. 测试连接
   1. 启动Spring Boot主类，是否有banner图案 
   2. 在单元测试中测试数据库连接
7. 测试访问项目的静态资源
## 用户注册
### 创建数据表
1. 选中数据库
2. 创建t_user表
```mysql
CREATE TABLE t_user (
	uid INT AUTO_INCREMENT COMMENT '用户id',
	username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
	password CHAR(32) NOT NULL COMMENT '密码',
	salt CHAR(36) COMMENT '盐值',
	phone VARCHAR(20) COMMENT '电话号码',
	email VARCHAR(30) COMMENT '电子邮箱',
	gender INT COMMENT '性别:0-女，1-男',
	avatar VARCHAR(50) COMMENT '头像',
	is_delete INT COMMENT '是否删除：0-未删除，1-已删除',
	created_user VARCHAR(20) COMMENT '日志-创建人',
	created_time DATETIME COMMENT '日志-创建时间',
	modified_user VARCHAR(20) COMMENT '日志-最后修改执行人',
	modified_time DATETIME COMMENT '日志-最后修改时间',
	PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
### 创建用户实体类
1. 通过表的结构提取出公共字段，放在一个实体类基类`BaseEntity`中
```java
@Data
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser; 
    private String modifiedTime;
    // Getter & Setter
}
```
2. 创建用户的实体类，需要继承`BaseEntity`基类
```java
@Component
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
    // Getter & Setter
}
```
### 注册-持久层
通过 MyBatis 来操作数据库
1. 规划需要执行的 sql 语句
```mysql
INSERT INTO t_user(username, password) VALUES(#{Username}, #{password})
```
2. 设计 Mapper 接口和抽象方法
3. 编写映射
   * 注解
   * xml
### 注册-业务层
#### 规划自定义异常
1. 使用具体的异常来继承 `RuntimeException` 异常。对于业务层的异常，设计一个 `ServiceException` 异常作为基类。
2. 注册时发现用户名被占用，设计 `UsernameDuplicatedException`
3. 插入数据时遭遇数据库宕机，设计 `InsertException`
#### 设计接口和抽象方法
1. 在 `service` 下创建 `UserService` 接口
2. 在 `service.impl` 下创建 `UserServiceImpl` 实现类
### 注册-控制层
#### 创建响应
状态码、状态描述信息和数据这部分功能封装在类 `com.cy.store.util.JsonResult` 中，将该类作为方法返回值返回给浏览器。
#### 设计请求
* 请求路径：/users/reg 
* 请求参数：User user
* 请求类型：POST
* 相应结果：`JsonResult<Void>`
#### 处理请求
1. 创建 `UserController` 类
### 注册-前端页面
1. 在 `register.html` 中编写发送请求的方法，点击事件来完成。选中对应的按钮（$("选择器")），再去添加点击的事件，`$.ajax()` 函数发送异步请求
2. jQuery 封装了 `$.ajax()` 函数，通过对象调用该函数可以异步加载相关的请求——依靠 JavaScript 提供的 XHR(XmlHttpResponse) 对象实现。
3. `$.ajax()` 接收方法体参数
```JavaScript
$.ajax({
    url: "", // 不能包含参数列表
    type: "", // GET/POST
    data: "", // 发送到服务器的数据。例如：data: "username=tom&pwd=123"
    dataType: "", // 预期服务器返回的数据类型。一般指定为json类型
    success: function () {
        
    },
    error: function () {
           
    }
});
```
4. sd1
## 用户登录
### 登录-持久层
1. 规划需要执行的 SQL 语句
2. 设计接口和方法
### 登录-业务层
1. 规划异常
	* 用户名对应的密码错误：`PasswordNotMatchedException`
	* 用户名未找到：`UserNotFoundException`
2. 设计业务层接口和抽象方法
   * 在 `UserSerive` 接口中编写抽象方法
3. 抽象方法实现
### 登录-控制层
1. 处理异常
2. 设计请求
```
请求路径：/users/login
请求方式：POST
请求数据：String username, String password
响应结果：JsonResult<User>
```
3. 处理请求

### 登录-前端页面

## 用户会话

session 对象主要存在服务器端，可以用于保存服务器的临时数据的对象，所保存的数据在整个项目中都可以通过访问获取，可被视作一个共享数据。首次登录的时候所获取的用户的数据，转移到 session 对象即可。`session.getAttribute("key")` 可以将获取 session 中数据的行为进行封装，封装在 `BaseController` 类中。

## 拦截器

将所有请求统一拦截到拦截器中。如果不满足系统设置的过滤规则，应重定向至 `login.html` 页面。

SpringBoot 依赖 SpringMVC 提供的 `HandlerInterceptor` 接口定义的拦截器。