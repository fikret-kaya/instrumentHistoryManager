package com.traderepublic.instrumentHistoryManager.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CandleStick {
    private LocalDateTime openTimestamp;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private LocalDateTime closeTimestamp;

    public CandleStick(LocalDateTime openTimestamp) {
        this.openTimestamp = openTimestamp;
        openPrice = 0;
        highPrice = 0;
        lowPrice = 0;
        closePrice = 0;
        closeTimestamp = openTimestamp.plusMinutes(1);
    }
}
