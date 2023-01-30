package com.db.trade.mgmt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.db.trade.mgmt.domain.RefTrade;

@Repository
@EnableJpaRepositories
public interface RefTradeRepo extends JpaRepository<RefTrade,Integer>{
	
}
