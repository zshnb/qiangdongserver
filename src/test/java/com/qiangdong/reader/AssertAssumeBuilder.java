package com.qiangdong.reader;

import java.time.LocalDateTime;
import java.util.Collection;
import org.hamcrest.Matchers;
import org.junit.Assert;

public class AssertAssumeBuilder<T extends Object & Comparable<T>> {
	private T value;

	private Object object;

	public AssertAssumeBuilder(T value) {
		this.value = value;
	}

	public AssertAssumeBuilder(Object object, Object o) {
		this.object = object;
	}

	public void isEqualTo(T target) {
		Assert.assertThat(value, Matchers.equalTo(target));
	}

	public void isEqualTo(LocalDateTime target) {
		Assert.assertThat(object, Matchers.equalToObject(target));
	}

	public void isNotEqualTo(T target) {
		Assert.assertNotEquals(target, value);
	}

	public void isTrue() {
		Assert.assertEquals(true, value);
	}

	public void isNotTrue() {
		Assert.assertEquals(false, value);
	}

	public void isZero() {
		Assert.assertEquals(0, value);
	}

	public void isNotZero() {
		Assert.assertNotEquals(0, value);
	}

	public void isGreaterThan(T target) {
		Assert.assertThat(value, Matchers.greaterThan(target));
	}

	public void isLessThan(T target) {
		Assert.assertThat(value, Matchers.lessThan(target));
	}

	public void isLessThanOrEqualTo(T target) {
		Assert.assertThat(value, Matchers.lessThanOrEqualTo(target));
	}

	public void isNull() {
		Assert.assertNull(object);
	}

	public void isNotNull() {
		Assert.assertNotNull(object);
	}

	public void isEmpty() {
		if (value instanceof Collection) {
			Assert.assertThat(((Collection) value).size(), Matchers.is(0));
		}
	}

	public void isNotEmpty() {
		if (value instanceof String) {
			Assert.assertThat(((String) value).length(), Matchers.greaterThan(0));
		}
	}

	public void isStartWith(String prefix) {
		if (value instanceof String) {
			Assert.assertThat((String) value, Matchers.startsWith(prefix));
		}
	}
}