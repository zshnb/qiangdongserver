package com.qiangdong.reader.annotation;

import com.qiangdong.reader.config.EnumValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {EnumValidator.class})
public @interface NotNone {
    String message() default "无效的枚举类型";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
