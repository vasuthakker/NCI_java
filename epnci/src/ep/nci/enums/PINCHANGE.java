package ep.nci.enums;

import epx.exception.ErrorCode;

public enum PINCHANGE implements ErrorCode {
	MOBILE_NUMBER_NOT_PROVIDED(1301),
	PIN_NOT_PROVIDED(1302),
	INCORRECT_OLD_PIN (1303),
	PROVIDE_DIFFERENT_PIN (1304),
	NOT_REGISTERED (1305);
	
	
	private final int number;

	private PINCHANGE(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}
	

}

