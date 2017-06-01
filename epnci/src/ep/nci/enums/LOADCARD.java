package ep.nci.enums;

import epx.exception.ErrorCode;

public enum LOADCARD implements ErrorCode {

	MOBILE_NUMBER_NOT_PROVIDED (1801);
	
	private final int number;

	private LOADCARD(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}


}


