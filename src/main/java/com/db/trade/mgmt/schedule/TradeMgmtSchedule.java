/**
 * 
 */
package com.db.trade.mgmt.schedule;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.domain.TradeTransmission;
import com.db.trade.mgmt.service.TradeMgmtService;

/**
 * @author Nishikanta
 *
 */
@Configuration
@EnableScheduling
public class TradeMgmtSchedule {
	
	@Autowired
	TradeMgmtService tradeMgmtService;
	
	
	
	@Scheduled(fixedDelay = 10000 , initialDelay = 1000)
	public void maturityDateCheckScheduler() {
		List<TradeTransmission> tradeTransitionLst = tradeMgmtService.loadAllTransition();
		
		List<Integer> expiredTradeLst = tradeTransitionLst.stream()
				.filter(e -> e.getMaturityDate().before(Timestamp.valueOf(LocalDateTime.now()))).map(m->m.getTradeSeqId())
				.collect(Collectors.toList());
	   
		if(null != expiredTradeLst && !expiredTradeLst.isEmpty()) {
			List<RefTrade> expiredRefTrade = tradeMgmtService.findRefTradeRepoLstBySeqIds(expiredTradeLst);
			expiredRefTrade.stream().forEach(e->e.setExpired(true));
			
			tradeMgmtService.saveRefTradeList(expiredRefTrade);	
		}
	}

}
