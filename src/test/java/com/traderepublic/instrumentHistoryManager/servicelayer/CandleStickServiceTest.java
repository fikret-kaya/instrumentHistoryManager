package com.traderepublic.instrumentHistoryManager.servicelayer;

import com.google.gson.Gson;
import com.traderepublic.instrumentHistoryManager.dto.InstrumentDto;
import com.traderepublic.instrumentHistoryManager.dto.QuoteDto;
import com.traderepublic.instrumentHistoryManager.model.Instrument;
import com.traderepublic.instrumentHistoryManager.model.Quote;
import com.traderepublic.instrumentHistoryManager.service.CandleStickService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
public class CandleStickServiceTest {
    @Autowired
    @InjectMocks
    private CandleStickService service;

    private Gson gson = new Gson();

    @Test
    public void addInstrumentToSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNotNull();
    }

    @Test
    public void addInstrumentToSystemWithNoIsin() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNull();
    }

    @Test
    public void addInstrumentToSystemWithNullIsin() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument(null,"elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNull();
    }

    @Test
    public void deleteExistingInstrumentFromSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto); // save
        instrumentDto.setType("DELETE");
        service.setInstrument(instrumentDto); // delete
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNull();
    }

    @Test
    public void deleteNonExistingInstrumentFromSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("DELETE",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto); // delete
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNull();
    }

    @Test
    public void addExistingInstrumentToSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto);
        service.setInstrument(instrumentDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNotNull();
    }

    @Test
    public void addExistingInstrumentAfterDeleteToSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        // when
        service.setInstrument(instrumentDto); // save
        instrumentDto.setType("DELETE");
        service.setInstrument(instrumentDto); // delete
        instrumentDto.setType("ADD");
        service.setInstrument(instrumentDto); // save again
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());

        // then
        then(candleStick).isNotNull();
    }

    @Test
    public void addQuoteToSystem() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        QuoteDto quoteDto = new QuoteDto("QUOTE",new Quote("LS342I184454",1365.25));

        // when
        service.setInstrument(instrumentDto);
        service.setQuote(quoteDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());
        boolean priceAdded = candleStick.contains("1365.25");

        // then
        then(candleStick).isNotNull();
        then(priceAdded).isEqualTo(true);
    }

    @Test
    public void addQuoteToSystemWithNoIsin() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("LS342I184454","elementum eos accumsan orci constituto antiopam"));

        QuoteDto quoteDto = new QuoteDto("QUOTE",new Quote("",1365.25));

        // when
        service.setInstrument(instrumentDto);
        service.setQuote(quoteDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());
        boolean priceAdded = candleStick.contains("1365.25");

        // then
        then(candleStick).isNotNull();
        then(priceAdded).isEqualTo(false);
    }

    @Test
    public void addQuoteToSystemWithWrongIsin() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("XYZ123TYU","elementum eos accumsan orci constituto antiopam"));

        QuoteDto quoteDto = new QuoteDto("QUOTE",new Quote("",1365.25));

        // when
        service.setInstrument(instrumentDto);
        service.setQuote(quoteDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());
        boolean priceAdded = candleStick.contains("1365.25");

        // then
        then(candleStick).isNotNull();
        then(priceAdded).isEqualTo(false);
    }

    @Test
    public void addQuoteToSystemWithNullIsin() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("XYZ123TYU","elementum eos accumsan orci constituto antiopam"));

        QuoteDto quoteDto = new QuoteDto("QUOTE",new Quote(null,1365.25));

        // when
        service.setInstrument(instrumentDto);
        service.setQuote(quoteDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());
        boolean priceAdded = candleStick.contains("1365.25");

        // then
        then(candleStick).isNotNull();
        then(priceAdded).isEqualTo(false);
    }

    @Test
    public void addQuoteToSystemWithXxx() {
        // given
        InstrumentDto instrumentDto = new InstrumentDto("ADD",
                new Instrument("XYZ123TYU","elementum eos accumsan orci constituto antiopam"));

        QuoteDto quoteDto = new QuoteDto("QUOTE",new Quote("",1365.25));

        // when
        service.setInstrument(instrumentDto);
        service.setQuote(quoteDto);
        String candleStick = service.getCandleSticks(instrumentDto.getData().getIsin());
        boolean priceAdded = candleStick.contains("1365.25");

        // then
        then(candleStick).isNotNull();
        then(priceAdded).isEqualTo(false);
    }
}
