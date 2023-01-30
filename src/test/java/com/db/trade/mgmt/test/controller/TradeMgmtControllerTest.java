/**
 * 
 */
package com.db.trade.mgmt.test.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.db.trade.mgmt.controller.TradeMgmtController;
import com.db.trade.mgmt.domain.RefTrade;
import com.db.trade.mgmt.domain.TradeTransmission;
import com.db.trade.mgmt.repo.RefTradeRepo;
import com.db.trade.mgmt.repo.TradeTransmissionRepo;
import com.db.trade.mgmt.req.TradeTransmissionReq;
import com.db.trade.mgmt.service.TradeMgmtService;
import com.db.trade.mgmt.test.config.TradeMgmtTestConfig;
import com.db.trade.mgmt.test.util.JsonUtil;



/**
 * @author Nishikanta
 *
 */
//@RunWith(SpringRunner.class)
@WebMvcTest(TradeMgmtController.class)
@Import(TradeMgmtTestConfig.class)
class TradeMgmtControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TradeMgmtService service;
    
	@MockBean
	RefTradeRepo refTradeRepo;
	
	@MockBean
	TradeTransmissionRepo tradeTransmissionRepo;
	
	@BeforeEach
	public void initialize () {
		
		List<RefTrade> refTradeLst = new ArrayList<RefTrade>();
		
		RefTrade refTrade = new RefTrade("T1", "Trade Name 1", 1l, false);
		refTrade.setSeqId(1);
		refTradeLst.add(refTrade);
		
		refTrade = new RefTrade("T2", "Trade Name 2", 2l, false);
		refTrade.setSeqId(2);
		refTradeLst.add(refTrade);
		
		refTrade = new RefTrade("T2", "Trade Name 2", 1l, false);
		refTrade.setSeqId(3);
		refTradeLst.add(refTrade);
		
		refTrade = new RefTrade("T3", "Trade Name 3", 3l, true);
		refTrade.setSeqId(4);
		refTradeLst.add(refTrade);
		
		given(refTradeRepo.findAll()).willReturn(refTradeLst);
		
		
		//Initializing TradeTransitionRepo
        TradeTransmission trade = new TradeTransmission();
        
		LocalDateTime today =  LocalDateTime.now();	
		LocalDateTime maturityDate = today.plusYears(3);
		trade.setSeqId(1);
        trade.setBookingId("B1");
        trade.setCpId("CP-1");
        trade.setCreationDate(Timestamp.valueOf(today));
        trade.setMaturityDate(Timestamp.valueOf(maturityDate));
        trade.setTradeSeqId(1);
		
        given(tradeTransmissionRepo.save(Mockito.any())).willReturn(trade);
        
        List<TradeTransmission> tradeLst = Arrays.asList(trade);

        given(service.loadAllTransition()).willReturn(tradeLst);
	}
    
    @Test
    public void createTradeTransmission_whenValidRequest_thenSaveAndReturnJson()
      throws Exception {
        
        TradeTransmissionReq req = new TradeTransmissionReq();
        req.setBookingId("B1");
        req.setCpId("CP-1");
        req.setTradeId("T1");
        req.setVersion(1l);
        req.setLockingPeriod(3);
        
		mvc.perform(post("/trade-mgmt/v1/initiate-trade").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(req)))
				.andExpect(status().isCreated());
		reset(service);
    }

    @Test
    public void createTradeTransmission_whenVersionIsLower_thenThrowException()
      throws Exception {
        
        TradeTransmissionReq req = new TradeTransmissionReq();
        req.setBookingId("B1");
        req.setCpId("CP-1");
        req.setTradeId("T2");
        req.setVersion(1l);
        req.setLockingPeriod(3);
        
		mvc.perform(post("/trade-mgmt/v1/initiate-trade").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(req)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Transmission can not be processed as the Lower Version Trade (i.e : 1 ) has been received")));
		reset(service);
    }
	
    
    @Test
    public void createTradeTransmission_whenMaturityDateLessThanCurrentDate_thenThrowException()
      throws Exception {
        
        TradeTransmissionReq req = new TradeTransmissionReq();
        req.setBookingId("B1");
        req.setCpId("CP-1");
        req.setTradeId("T2");
        req.setVersion(2l);
        req.setLockingPeriod(-3);
        
		mvc.perform(post("/trade-mgmt/v1/initiate-trade").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(req)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("Transmission can not be processed as Maturity Date")));
		reset(service);
    }
    
    @Test
    public void givenTradeTransmission_whenGetAllTradeTransmission_thenReturnJsonArray()
      throws Exception {
        
        mvc.perform(get("/trade-mgmt/v1/all-trades")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].cpId", is("CP-1")));
    }

    
    @Test
    public void whenGetAllRefTrade_thenReturnJsonArray()
      throws Exception { 	
       mvc.perform(get("/trade-mgmt/v1/trade-refs")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(4)));
    }

}
