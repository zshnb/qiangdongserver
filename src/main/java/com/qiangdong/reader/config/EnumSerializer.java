package com.qiangdong.reader.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qiangdong.reader.enums.BaseEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 序列化枚举，以json形式返回前端时序列化其code值
 * */
@Component
public class EnumSerializer extends JsonSerializer<BaseEnum> {
    @Override
    public void serialize(BaseEnum baseEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(String.valueOf(baseEnum.code()));
    }

    @Override
    public Class<BaseEnum> handledType() {
       return BaseEnum.class;
    }
}
