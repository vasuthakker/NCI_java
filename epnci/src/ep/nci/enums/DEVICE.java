package ep.nci.enums;

import epx.exception.ErrorCode;

public enum DEVICE implements ErrorCode {

	OTP_NOT_PROVIDED(1001),
	OTP_EXPIRED(1002),
	CUSTOMER_OTP_ATTEMPTS_EXCEEDS(1003),
	OTP_SUCCESSFULLY_VERIFIED(1004),
	OTP_NOT_MATCH(1005),
	MOBILE_NUMBER_NOT_PROVIDED(1006),
	OTP_VERIFICATION_FAILED (1007),
	INVALID_OTP_OR_MOBILE_NUMBER(1008);
	
	private final int number;

	private DEVICE(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}


}

