package com.qiangdong.reader.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class MybatisDateTimeMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "createAt", LocalDateTime.class, LocalDateTime.now());
		this.strictInsertFill(metaObject, "updateAt", LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "updateAt", LocalDateTime.class, LocalDateTime.now());
	}
}
