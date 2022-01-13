package com.traderepublic.instrumentHistoryManager.dto;

import com.traderepublic.instrumentHistoryManager.model.Instrument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentDto {
    private String type;
    private Instrument data;

}
