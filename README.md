### 墙洞app

#### 调用serviceImpl包里的方法前校验请求参数里的值
校验的通常是值合法性，不涉及业务逻辑层的合法性
- 请求类继承`BaseRequest`
- 在`validate`包下创建校验请求的类，继承RequestValidator，并重写validate方法
- 在serviceImpl类的方法上加注解`@Validation(xxxValidator.class)`
- 某个模块的校验类都放同一个包下，校验类的名称以请求方法开头，比如AddXXXValidator

#### 全局异常处理
目前已有异常：
- InvalidArgumentException：请求参数不合法，返回BAD_REQUEST
- InternalException：服务器错误，返回INTERNAL_ERROR
- DuplicatedKeyException：sql插入主键重复错误，返回INTERNAL_ERROR
- PermissionDenyException：认证未通过，返回FORBIDDEN

### 单元测试中测试方法抛出指定异常
```java
assertException(xxxException.class, () -> {
    这里写执行的业务逻辑
});
```

### jwt授权
JwtUtil.signJwtToken()是签发，拦截器会拦截所有请求，验证request header中携带的token是否有限，无效返回403 FORBIDDEN。
@RequestBody修饰的Request类中的userId会在拦截器通过拦截后自动注入。
过滤顺序：http request -> RequestWrapperFilter(构造RequestInjectUserIdWrapper，并传入接下来的拦截器) ->
JwtInterceptor(验证JwtToken是否合法，合法则自动注入userId) -> controller

### DeleteMapping path variable 自动注入
在@DeleteMapping里的url，如果带有占位符，且占位符名称存在请求的Request类里，那么会自动把请求时占位符真实的值注入Request相应的变量里

### 单元测试断言
assertThat(value).断言方法
目前支持断言方法
- isEqualTo: 值是否相等
- isNotEqualTo
- isTrue: 值为true
- isNotTrue: 值不是true
- isZero: 值是0
- isGreaterThan: 值是否大于 
- isNull
- isNotNull

### 业务逻辑断言
- AssertUtil.assertNull(object, message): 断言object为null，否则抛出InvalidArgumentException，错误消息为message
- AssertUtil.assertNotNull(object, message): 断言object不为null，否则抛出InvalidArgumentException，错误消息为message

### serviceImpl方法自动注入实体，并在不存在的时候自动抛异常
在XXXRequest里存在xxxId，比如novelId，然后在使用request的方法里在request参数后面定义id对应的实体参数，比如
```java
void getNovel(GetNovelRequest request, Novel novel);
```
novel将自动注入为novelId对应的数据库记录实体，controller方法里调用时需要把参数占个空，比如new Type(), 测试里同样。

### 权限检查
在service方法上加以下注解即可在进入service方法前检查调用userId对应的user是否有相应权限，
若没有，则抛出403状态码
- RequireAdmin：要求管理员
- RequireEditor：要求编辑
- RequireAuthor：要求作者
- RequireAuthorOrEditor：要求作者或者编辑

示例：
```java
@RequireAdmin
void requireAdmin() {

}
```

### RequestBody请求参数校验
全局对controller接收到前端的参数做校验，比如对字符串长度，数字大小等
- 在controller的请求类前面加注解`@Valid`
- 在Request类的字段上加校验注解，常用如下
  - @Size(min = 0, max = 50)：字符串长度限制
  - @NotNone：枚举不能为NONE