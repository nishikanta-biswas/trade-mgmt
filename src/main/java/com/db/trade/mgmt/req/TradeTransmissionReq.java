/**
 * 
 */
package com.db.trade.mgmt.req;

/**
 * @author Nishikanta
 *
 */

public class TradeTransmissionReq {

	
	private String tradeId;
	
	private Long version;
	
	private String cpId;
	
	private String bookingId;
	
	private Integer lockingPeriod;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getLockingPeriod() {
		return lockingPeriod;
	}

	public void setLockingPeriod(Integer lockingPeriod) {
		this.lockingPeriod = lockingPeriod;
	}
	

	
}
