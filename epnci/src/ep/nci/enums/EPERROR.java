/**
 * 
 */
package ep.nci.enums;

import epx.exception.ErrorCode;

/**
 * @author Pooja
 * @date 25-Mar-2017
 *
 */
public enum EPERROR implements ErrorCode{

	SERVER_ERROR(999),
	SERVER_CONFIG_ERROR(955),
	DB_CONNECTION_ERROR(988);

	private final int number;

	private EPERROR(int number) {
		this.number = number;
	}
	@Override
	public Object getNumber() {
		return number;
	}

}

