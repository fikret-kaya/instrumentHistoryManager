package com.traderepublic.instrumentHistoryManager.controllerlayer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.traderepublic.instrumentHistoryManager.controller.InstrumentController;
import com.traderepublic.instrumentHistoryManager.dto.InstrumentDto;
import com.traderepublic.instrumentHistoryManager.dto.QuoteDto;
import com.traderepublic.instrumentHistoryManager.model.Instrument;
import com.traderepublic.instrumentHistoryManager.model.Quote;
import com.traderepublic.instrumentHistoryManager.service.CandleStickService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class InstrumentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    CandleStickService service;

    @InjectMocks
    private InstrumentController controller;

    String response = "[{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":15,\"second\":53,\"nano\":286795000}},\"openPrice\":1109.6389,\"highPrice\":1109.6389,\"lowPrice\":1057.9444,\"closePrice\":1057.9444,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":16,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":16,\"second\":53,\"nano\":286795000}},\"openPrice\":1023.8056,\"highPrice\":1029.6667,\"lowPrice\":948.6944,\"closePrice\":948.6944,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":17,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":17,\"second\":53,\"nano\":286795000}},\"openPrice\":951.5556,\"highPrice\":951.5556,\"lowPrice\":864.4444,\"closePrice\":864.4444,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":18,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":18,\"second\":53,\"nano\":286795000}},\"openPrice\":845.3056,\"highPrice\":850.1667,\"lowPrice\":728.7778,\"closePrice\":728.7778,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":19,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":19,\"second\":53,\"nano\":286795000}},\"openPrice\":733.6389,\"highPrice\":733.6389,\"lowPrice\":625.25,\"closePrice\":625.25,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":20,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":20,\"second\":53,\"nano\":286795000}},\"openPrice\":630.1111,\"highPrice\":630.1111,\"lowPrice\":542,\"closePrice\":551.3077,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":21,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":21,\"second\":53,\"nano\":286795000}},\"openPrice\":555.6154,\"highPrice\":582.1538,\"lowPrice\":555.6154,\"closePrice\":581.7692,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":22,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":22,\"second\":53,\"nano\":286795000}},\"openPrice\":577.0769,\"highPrice\":629.1538,\"lowPrice\":577.0769,\"closePrice\":629.1538,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":23,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":23,\"second\":53,\"nano\":286795000}},\"openPrice\":620.4615,\"highPrice\":690.7692,\"lowPrice\":620.4615,\"closePrice\":690.7692,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":24,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":24,\"second\":53,\"nano\":286795000}},\"openPrice\":705.0769,\"highPrice\":764.7692,\"lowPrice\":696.3846,\"closePrice\":749.0769,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":25,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":25,\"second\":53,\"nano\":286795000}},\"openPrice\":759.3846,\"highPrice\":816.4615,\"lowPrice\":758.6154,\"closePrice\":805.7692,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":26,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":26,\"second\":53,\"nano\":286795000}},\"openPrice\":802.0769,\"highPrice\":852.5385,\"lowPrice\":802.0769,\"closePrice\":852.5385,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":27,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":27,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":885,\"lowPrice\":849.1538,\"closePrice\":866.3077,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":28,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":28,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":878.6154,\"lowPrice\":849.1538,\"closePrice\":878.6154,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":29,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":29,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":30,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":30,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":31,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":31,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":32,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":32,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":33,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":33,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":34,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":34,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":35,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":35,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":36,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":36,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":37,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":37,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":38,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":38,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":39,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":39,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":40,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":40,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":41,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":41,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":42,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":42,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":43,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":43,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":44,\"second\":53,\"nano\":286795000}}},{\"openTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":44,\"second\":53,\"nano\":286795000}},\"openPrice\":857.8462,\"highPrice\":868.3846,\"lowPrice\":849.1538,\"closePrice\":862.6923,\"closeTimestamp\":{\"date\":{\"year\":2021,\"month\":11,\"day\":24},\"time\":{\"hour\":0,\"minute\":45,\"second\":53,\"nano\":286795000}}}]";
    String errorResponse = "Instrument not found!";

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getCandleStickSuccess() throws Exception{
        when(service.getCandleSticks(any())).thenReturn(asJsonString(response));
        MvcResult mvcResult = mockMvc.perform(get("/candlesticks?isin={ISIN}", "LS342I184454"))
                        .andExpect(status().isOk()).andReturn();
        verify(service, times(1)).getCandleSticks(any());

        then(mvcResult.getResponse().getContentAsString()).isEqualTo(asJsonString(response));
    }

    @Test
    public void getCandleStickBadRequest() throws Exception {
        //when(service.getCandleSticks(any())).thenReturn(asJsonString(errorResponse));
        mockMvc.perform(get("/candlesticks?isin={ISIN}", ""))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
