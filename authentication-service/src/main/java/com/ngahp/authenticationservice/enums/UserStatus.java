package com.ngahp.authenticationservice.enums;

public enum UserStatus {
    UNVERIFIED, ACTIVE, BLOCK, WAITING_FOR_APPROVAL, REJECTED, PENDING;

	public static UserStatus getStatus(String status) {
		try {
			return UserStatus.valueOf(status);
		} catch (Exception e) {
			return null;
		}
	}
}
