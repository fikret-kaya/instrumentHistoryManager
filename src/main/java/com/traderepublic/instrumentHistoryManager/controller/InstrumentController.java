package com.traderepublic.instrumentHistoryManager.controller;

import com.traderepublic.instrumentHistoryManager.service.CandleStickService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstrumentController {

    Logger logger = LoggerFactory.getLogger(InstrumentController.class);

    @Autowired
    private CandleStickService service;

    @GetMapping("/candlesticks")
    public ResponseEntity<String> getCandleSticks(@RequestParam String isin) {
        String response = service.getCandleSticks(isin);

        if (response == null) {
            logger.error("New request received for ISIN: " + isin + " (BUT NOT FOUND!)");
            return new ResponseEntity<>("Instrument not found!", HttpStatus.BAD_REQUEST);
        }

        logger.info("New request received for ISIN: " + isin);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
