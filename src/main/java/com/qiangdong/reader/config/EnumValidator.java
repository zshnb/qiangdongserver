package com.qiangdong.reader.config;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.BaseEnum;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<NotNone, BaseEnum> {

    @Override
    public boolean isValid(BaseEnum value, ConstraintValidatorContext constraintValidatorContext) {
        return value.code() != 0;
    }
}
