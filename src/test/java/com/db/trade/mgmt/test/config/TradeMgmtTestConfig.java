/**
 * 
 */
package com.db.trade.mgmt.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.db.trade.mgmt.domain.LoadReferenceData;
import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.repo.RefTradeRepo;

/**
 * @author chait
 *
 */
@TestConfiguration
public class TradeMgmtTestConfig {	
	
	@Autowired
	RefTradeRepo refTradeRepo;
	
	
	@Bean("refTradeData")
	public LoadReferenceData<RefTrade, RefTradeRepo> loadTradeRefData(){
		return new LoadReferenceData<RefTrade, RefTradeRepo>(refTradeRepo);
	}
}
