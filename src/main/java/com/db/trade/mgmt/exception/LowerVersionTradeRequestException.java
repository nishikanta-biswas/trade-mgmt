/**
 * 
 */
package com.db.trade.mgmt.exception;

/**
 * @author Nishikanta
 *
 */
public class LowerVersionTradeRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LowerVersionTradeRequestException(String message) {
		super(message);
	}
	

}
