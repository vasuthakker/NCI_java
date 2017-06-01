package ep.nci.enums;

import epx.exception.ErrorCode;

public enum CARDSERVICE implements ErrorCode {

	MOBILENUMBER_NOT_PROVIDED (2001);
	
	private final int number;

	private CARDSERVICE(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}


}

