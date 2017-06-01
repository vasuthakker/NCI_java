/**
 * 
 */
package ep.nci.enums;

import epx.exception.ErrorCode;

/**
 * @author pooja
 * @date 25-Mar-2017
 */
public enum INPUT implements ErrorCode {

	INVALID_JSON_FORMAT(799),
	DUPLICATE_ORDER_ID(755),
	REQUIRED_PARAMETER_NOT_FOUND(744),
	JSON_REQPARM_NOT_FOUND(745),
	INVALID_INPUT(746);
	
	
	private final int number;

	private INPUT(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}


}
