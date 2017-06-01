/**************************************************************************************************
 * File Name : EasyAppException.java
 * @author   : POOJA 
 * Version Information : 1.0
 * Customized Exception for all Easy Pay Exceptions
 * Modification History:
 * Date		 Modified By		Description 
 *18-03-2015  POOJA			User Defined Exception Class
 ************************************************************************************************* */
package ep.nci.exceptions;

import ep.nci.exceptions.MessageInfoCache;

/**
 * Class defines various constructors that can be used for invoking exceptions
 * @author POOJA
 */
public class EasyAppException extends Exception{

	static final long serialVersionUID = -5829545098534135052L;
	private String exceptionMessage;   
    private String errorCode;
    private String loggerMessage;

    /**
     * A public constructor for EasyAppException containing no arguments.
     *  
     */
    public EasyAppException() {
    }

    /**
     * A public constructor for EasyAppException containing two arguments.
     *  1st is any user friendly message and the other is error code,
     *  invoked for checked exceptions.
     */
    
    public EasyAppException(String msg, String errorCode) {
    	super(MessageInfoCache.getMessageString(errorCode));
    	this.exceptionMessage = MessageInfoCache.getMessageString(errorCode);
    	this.loggerMessage = msg;
    	this.errorCode = errorCode;
    }
    
    /**
     * A public constructor for EasyAppException containing one argument,
     * invoked for checked exceptions
     *  
     */
    public EasyAppException(String errorCode) {
    	super(MessageInfoCache.getMessageString(errorCode));
    	this.exceptionMessage = MessageInfoCache.getMessageString(errorCode);
    	this.errorCode = errorCode;
    }

    /**
     * A public constructor ofEasyAppException containing
     * message and root cause (asThrowable) of the exception.
     * Invoked for unchecked exceptions
     */
    public EasyAppException(String msg, Throwable e) {
        this.exceptionMessage = msg;
        this.initCause(e);
        this.loggerMessage = msg;
        this.errorCode = MessageInfoCache.getMessageString("err_000");
    }

    /**
     * sets the root cause of the exception. Used for setting Java built in
     * exception in EasyAppException.
     *  
     */
    public void setCause(Throwable e) {
        this.initCause(e);
    }

    /**
     * Gets the class name and exception message.
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String s = getClass().getName();
        return s + ": " + exceptionMessage;
    }

    /**
     * Gets the message of the exception. equivalent to Exception.getMessage().
     */
    public String getMessage() {
    	return exceptionMessage;
    }
    
    /**
     * Gets the logger message of the exception.
     */
    public String getLoggerMessage() {
    	return loggerMessage;
    }
    
    /**
     * Gets the errorCode.
     */
    public String getErrorCode() {
    	return errorCode;
    }
}

