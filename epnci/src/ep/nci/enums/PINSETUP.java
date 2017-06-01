package ep.nci.enums;

import epx.exception.ErrorCode;

public enum PINSETUP implements ErrorCode {

	MOBILE_NUMBER_NOT_PROVIDED (1201),
	PIN_NOT_PROVIDED (1202),
	PIN_NOT_MATCHING (1203),
	MOBILE_NUMBER_NOT_REGISTERED (1204),
	DEVICE_IS_ALREADY_SETUP (1205);
	
	private final int number;

	private PINSETUP(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}

}

