/**
 * 
 */
package com.db.trade.mgmt.domain;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Nishikanta
 *
 */
@Entity
@Table(name = "TRADE_TRANSMISSION")
public class TradeTransmission {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer seqId;
	
	@Column(name = "TRADE_SEQ_ID")
	private Integer tradeSeqId;
	
	@Column(name = "COUNTER_PARTY_ID")
	private String cpId;
	
	@Column(name = "BOOKING_ID")
	private String bookingId;
	
	@Column(name = "CREATE_DATE")
	private Timestamp creationDate;
	
	@Column(name = "MATURITY_DATE")
	private Timestamp maturityDate;

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
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

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Timestamp maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Integer getTradeSeqId() {
		return tradeSeqId;
	}

	public void setTradeSeqId(Integer tradeSeqId) {
		this.tradeSeqId = tradeSeqId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tradeSeqId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeTransmission other = (TradeTransmission) obj;
		return Objects.equals(tradeSeqId, other.tradeSeqId);
	}


	
	
	
}
