package com.ngahp.userservice.util;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {
	public static String generateUUIDString() {
		return UUID.randomUUID().toString();
	}
}
