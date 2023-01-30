/**
 * 
 */
package com.db.trade.mgmt.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.trade.mgmt.domain.LoadReferenceData;
import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.domain.TradeTransmission;
import com.db.trade.mgmt.exception.LowerVersionTradeRequestException;
import com.db.trade.mgmt.exception.MaturityDatePastException;
import com.db.trade.mgmt.repo.RefTradeRepo;
import com.db.trade.mgmt.req.TradeTransmissionReq;
import com.db.trade.mgmt.res.TradeTransmissionRes;
import com.db.trade.mgmt.service.TradeMgmtService;

/**
 * @author Nishikanta
 *
 */
@CrossOrigin(origins = "*" , maxAge = 3600)
@RequestMapping("trade-mgmt/v1")
@RestController
public class TradeMgmtController {

	@Autowired
	@Qualifier("refTradeData")
	LoadReferenceData<RefTrade, RefTradeRepo> refTradeData;
	
	@Autowired
	TradeMgmtService tradeMgmtService;
	
	@GetMapping("/trade-refs")
	public ResponseEntity<List<RefTrade>> loadAllRefTrades() {		
		return new ResponseEntity<List<RefTrade>>(refTradeData.getAllRefLst(),HttpStatus.OK);
	}
	
	@PostMapping("/initiate-trade")
	public ResponseEntity<TradeTransmission> createTransition(@RequestBody TradeTransmissionReq req) throws Exception {
		
		//Check the higher version trade 
		long higherVersionCount = refTradeData.getAllRefLst().stream()
				.filter(e -> e.getTradeId().equals(req.getTradeId()) && e.getVersion().longValue() > req.getVersion().longValue()).count();
		
		if(higherVersionCount > 0) {
			throw new LowerVersionTradeRequestException("Transmission can not be processed as the Lower Version Trade (i.e : " + req.getVersion() + " ) has been received");
		}
		
		RefTrade refTrade = refTradeData.getAllRefLst().stream()
				.filter(e -> e.getTradeId().equals(req.getTradeId()) && e.getVersion().equals(req.getVersion()))
				.findFirst().get();
		LocalDateTime today =  LocalDateTime.now();	
		LocalDateTime maturityDate = today.plusYears(req.getLockingPeriod());
		
		//Validating maturity Date
		if (maturityDate.isBefore(today)) {
			throw new MaturityDatePastException("Transmission can not be processed as Maturity Date  is (i.e : "
					+ Date.from(maturityDate.atZone(ZoneId.systemDefault()).toInstant()) + " ) is less than today's date");
		}

		List<TradeTransmission> tradeTransmissionLst = tradeMgmtService.loadAllTransition().stream()
				.filter(e -> e.getTradeSeqId().equals(refTrade.getSeqId())).collect(Collectors.toList());
		TradeTransmission tradeTransmission = null;
		if(null == tradeTransmissionLst || tradeTransmissionLst.isEmpty()) {
			tradeTransmission = new TradeTransmission();
		}else {
			tradeTransmission = tradeTransmissionLst.get(0);
		}
		
		tradeTransmission.setBookingId(req.getBookingId());
		tradeTransmission.setCpId(req.getCpId());
		tradeTransmission.setTradeSeqId(refTrade.getSeqId());
		tradeTransmission.setCreationDate(Timestamp.valueOf(today));
		tradeTransmission.setMaturityDate(Timestamp.valueOf(maturityDate));

		return new ResponseEntity<TradeTransmission>(tradeMgmtService.createTransition(tradeTransmission),
				HttpStatus.CREATED);
	}
	
	@GetMapping("/all-trades")
	public ResponseEntity<List<TradeTransmissionRes>> loadAllTrade() {

		List<TradeTransmission> tradeTransitionLst = tradeMgmtService.loadAllTransition();

		List<TradeTransmissionRes> resLst = tradeTransitionLst.parallelStream().map(t -> {

			RefTrade refTrade = refTradeData.getAllRefLst().stream().filter(e -> e.getSeqId().equals(t.getTradeSeqId()))
					.findFirst().get();

			TradeTransmissionRes res = new TradeTransmissionRes();
			res.setBookingId(t.getBookingId());
			res.setCpId(t.getCpId());
			res.setCreateDate(t.getCreationDate());
			res.setMaturityDate(t.getMaturityDate());
			res.setTradeId(refTrade.getTradeId());
			res.setExpired(refTrade.getExpired());
			res.setVersion(refTrade.getVersion());
			return res;
		}).collect(Collectors.toList());
		
		return new ResponseEntity<List<TradeTransmissionRes>>(resLst,HttpStatus.OK);
	}
}
