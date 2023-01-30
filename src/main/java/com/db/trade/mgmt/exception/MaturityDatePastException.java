/**
 * 
 */
package com.db.trade.mgmt.exception;

/**
 * @author Nishikanta
 *
 */
public class MaturityDatePastException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MaturityDatePastException(String message) {
		super(message);
	}
	

}
