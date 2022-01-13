package com.traderepublic.instrumentHistoryManager.dto;

import com.traderepublic.instrumentHistoryManager.model.Quote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {
    private String type;
    private Quote data;
}
