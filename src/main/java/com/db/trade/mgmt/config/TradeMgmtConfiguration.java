/**
 * 
 */
package com.db.trade.mgmt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.db.trade.mgmt.domain.LoadReferenceData;
import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.repo.RefTradeRepo;

/**
 * @author Nishikanta
 *
 */
@Configuration

public class TradeMgmtConfiguration {

	@Autowired
	RefTradeRepo refTradeRepo;
	
	
	@Bean("refTradeData")
	public LoadReferenceData<RefTrade, RefTradeRepo> loadTradeRefData(){
		return new LoadReferenceData<RefTrade, RefTradeRepo>(refTradeRepo);
	}
	
	
}
