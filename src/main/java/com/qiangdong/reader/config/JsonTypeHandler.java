package com.qiangdong.reader.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonTypeHandler<T extends Object> extends BaseTypeHandler<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonTypeHandler.class);

	private static final ObjectMapper mapper = new ObjectMapper();

	private Class<T> clazz;

	static {
		mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public JsonTypeHandler(Class<T> clazz) {
		if (clazz == null) {
			throw new NullPointerException("Type argument cannot be null");
		}
		this.clazz = clazz;
	}

	/**
	 * object转json string
	 * @param object
	 * @return
	 */
	private String toJSON(T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			LOGGER.error("covert object to json string failed",e);
		}
		return null;
	}

	/**
	 * json转object
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	private T toObject(String json, Class<T> clazz) throws IOException {
		if (json != null && !json.equals("")) {
			return mapper.readValue(json,clazz);
		}
		return null;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter,
	                                JdbcType jdbcType) {
		try {
			ps.setString(i,toJSON(parameter));
		} catch (Exception e) {
			LOGGER.error("preparedStatement set string failed", e);
		}
	}

	@Override
	public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
		try {
			return toObject(resultSet.getString(s), clazz);
		} catch (IOException e) {
			LOGGER.error("convert json string to object failed",e);
		}
		return null;
	}

	@Override
	public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
		try {
			return toObject(resultSet.getString(i), clazz);
		} catch (IOException e) {
			LOGGER.error("convert json string to object failed",e);
		}
		return null;
	}

	@Override
	public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		try {
			return toObject(callableStatement.getString(i), clazz);
		} catch (IOException e) {
			LOGGER.error("convert json string to object failed",e);
		}
		return null;
	}
}
