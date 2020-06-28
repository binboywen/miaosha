package com.imooc.miaosha_2.util;

import java.util.UUID;

public class UUIDUtil {
	public static String uuid() {

		return UUID.randomUUID().toString().replace("-", "");

	}
}
