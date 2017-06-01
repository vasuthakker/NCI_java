package ep.nci.enums;

import epx.exception.ErrorCode;

public enum LOGIN implements ErrorCode {
	MOBILE_NUMBER_NOT_PROVIDED(1051),
	PIN_NOT_PROVIDED(1052),
	//MOBILE_NUMBER_NOT_REGISTERED(1052),
	MOBILE_NUMBER_AND_PIN_NOT_MATCHING(1053);
	
	private final int number;

	private LOGIN(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}
	

}
