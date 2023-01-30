/**
 * 
 */
package com.db.trade.mgmt.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * @author Nishikanta
 *
 */
@Entity
@Table(name = "REF_TRADE")
public class RefTrade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer seqId;
	
	@Column(name = "TRADE_ID")
	private String tradeId;
	
	@Column(name = "TRADE_NAME")
	private String tradeName;
	
	@Column(name = "VERSION")
	private Long version;
	
	@Transient
	private Boolean expired;
	
	@Column(name = "EXPIRATION_FLAG")
	private Integer expirationFlag;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getExpired() {
		
		if(null != getExpirationFlag() && getExpirationFlag().intValue() == 100 ) {
			return false;
		}		
		return true;
	}

	public void setExpired(Boolean expired) {
		if(null != expired && expired.booleanValue()) {
			setExpirationFlag(101);
		}else {
			setExpirationFlag(100);
		}
	}

	public Integer getExpirationFlag() {
		return expirationFlag;
	}

	public void setExpirationFlag(Integer expirationFlag) {
		this.expirationFlag = expirationFlag;
	}

	public Integer getSeqId() {
		return seqId;
	}

	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(seqId,expirationFlag, tradeId, tradeName, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefTrade other = (RefTrade) obj;
		return Objects.equals(seqId, other.seqId) && Objects.equals(expirationFlag, other.expirationFlag) && Objects.equals(tradeId, other.tradeId)
				&& Objects.equals(tradeName, other.tradeName) && Objects.equals(version, other.version);
	}

	public RefTrade(String tradeId, String tradeName, Long version, Boolean expired) {
		super();
		this.tradeId = tradeId;
		this.tradeName = tradeName;
		this.version = version;
		this.expired = expired;
	}
	
	public RefTrade() {
		super();
	}
	
}
