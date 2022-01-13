package com.traderepublic.instrumentHistoryManager.service;

import com.google.gson.Gson;
import com.traderepublic.instrumentHistoryManager.dto.InstrumentDto;
import com.traderepublic.instrumentHistoryManager.dto.QuoteDto;
import com.traderepublic.instrumentHistoryManager.model.CandleStick;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CandleStickService {

    private HashMap<String, List<CandleStick>> instruments;
    private Gson gson;

    public CandleStickService() {
        instruments = new HashMap<String, List<CandleStick>>();
        gson = new Gson();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                candleStickUpdate();
            }
        }, 1800000, 60000);
    }

    // adds or deletes instrument from hash map
    // when adding to map, initializes the candle sticks of the isin
    public void setInstrument(InstrumentDto instrumentDto) {
        if (instrumentDto.getData().getIsin() == null ||
                instrumentDto.getData().getIsin().equalsIgnoreCase("")) {
            return;
        }

        if (instrumentDto.getType().equalsIgnoreCase("ADD")) {
            List<CandleStick> candleSticks = new ArrayList<>(30);
            LocalDateTime openTimestamp = LocalDateTime.now();

            for (int i = 0; i < 30; i++) {
                candleSticks.add(new CandleStick(openTimestamp.plusMinutes(i)));
            }

            instruments.put(instrumentDto.getData().getIsin(), candleSticks);
        } else {
            instruments.remove(instrumentDto.getData().getIsin());
        }
    }

    // adds received price to given isin if found,
    // when adding the price, the candle stick data is updated
    public void setQuote(QuoteDto quote) {
        if (instruments.containsKey(quote.getData().getIsin())) {
            List<CandleStick> candleSticks = instruments.get(quote.getData().getIsin());
            LocalDateTime now = LocalDateTime.now();

            int diffTime = (int) ChronoUnit.MINUTES.between(candleSticks.get(0).getOpenTimestamp(), now);
            double price = quote.getData().getPrice();
            CandleStick candleStick = candleSticks.get(diffTime);

            if (candleStick.getOpenPrice() == 0) {
                candleStick.setOpenPrice(price);
                candleStick.setClosePrice(price);
                candleStick.setHighPrice(price);
                candleStick.setLowPrice(price);
            } else {
                candleStick.setClosePrice(price);
                if (candleStick.getHighPrice() < price) {
                    candleStick.setHighPrice(price);
                } else if (candleStick.getLowPrice() > price) {
                    candleStick.setLowPrice(price);
                }
            }
        }
    }

    // runs every 1 minute after initial 30 minutes
    // updates candle stick list by removing latest data (the one received after 30 minutes) and adds a new data
    private void candleStickUpdate() {
        for (String key : instruments.keySet()) {
            List<CandleStick> candleSticks = instruments.get(key);
            candleSticks.remove(0);
            candleSticks.add(29,
                    new CandleStick(candleSticks.get(28).getCloseTimestamp()));
        }
    }

    // returns null if no isin found
    // return candle sticks for the last 30 minutes, including missing minutes
    public String getCandleSticks(String isin) {
        if (instruments.containsKey(isin)) {
            List<CandleStick> candleSticks = instruments.get(isin);

            for (int i = 1; i < 30; i++) {
                if (candleSticks.get(i).getOpenPrice() == 0) {
                    candleSticks.get(i).setOpenPrice(candleSticks.get(i-1).getOpenPrice());
                    candleSticks.get(i).setClosePrice(candleSticks.get(i-1).getClosePrice());
                    candleSticks.get(i).setHighPrice(candleSticks.get(i-1).getHighPrice());
                    candleSticks.get(i).setLowPrice(candleSticks.get(i-1).getLowPrice());
                }
            }
            return gson.toJson(candleSticks);
        }
        return null;
    }
}
