/**
 * 
 */
package com.db.trade.mgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.domain.TradeTransmission;
import com.db.trade.mgmt.repo.RefTradeRepo;
import com.db.trade.mgmt.repo.TradeTransmissionRepo;

/**
 * @author chait
 *
 */

@Service
public class TradeMgmtService {

	@Autowired
	TradeTransmissionRepo tradeTransmissionRepo;
	
	@Autowired
	RefTradeRepo refTradeRepo;
	
	public TradeTransmission createTransition(TradeTransmission tradeTransmission) {
		return tradeTransmissionRepo.save(tradeTransmission);
	}
	
	public List<TradeTransmission> loadAllTransition() {
		return tradeTransmissionRepo.findAll();
	}
	
	public RefTrade findRefTradeRepoBySeqId(Integer seqId) {
		return refTradeRepo.findById(seqId).get();
	}
	
	public List<RefTrade> findRefTradeRepoLstBySeqIds(List<Integer> seqIdLst) {
		return refTradeRepo.findAllById(seqIdLst);
	}
	
	public List<RefTrade> saveRefTradeList(List<RefTrade> refTradeLst){
		return refTradeRepo.saveAll(refTradeLst);
	}
}
