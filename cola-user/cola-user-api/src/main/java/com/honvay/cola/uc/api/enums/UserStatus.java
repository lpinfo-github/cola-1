package com.honvay.cola.uc.api.enums;

/**
 * 用户状态枚举
 *
 * @author LIQIU
 * created on 2018-11-16
 **/
public enum UserStatus {

	/**
	 * 正常状态
	 */
	ACTIVE("正常", 1),
	/**
	 * 锁定状态
	 */
	LOCKED("锁定", 2),
	/**
	 * 失效状态
	 */
	DISABLED("失效", 3);

	UserStatus(String name, Integer value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * 状态名称
	 */
	private String name;

	/**
	 * 状态值
	 */
	private Integer value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
