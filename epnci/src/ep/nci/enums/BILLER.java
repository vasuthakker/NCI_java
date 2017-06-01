package ep.nci.enums;

import epx.exception.ErrorCode;

public enum BILLER implements ErrorCode {

	REQUEST_COMPLETE(100),
	BILLER_SERVICE_NOT_REACHABLE (888);
	
	private final int number;

	private BILLER(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}


}
