package com.traderepublic.instrumentHistoryManager.socket;

import com.google.gson.Gson;
import com.traderepublic.instrumentHistoryManager.service.CandleStickService;
import com.traderepublic.instrumentHistoryManager.dto.QuoteDto;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuoteStream {

    @Autowired
    private CandleStickService instrumentData;

    @Value("${partner.url}")
    private String partnerServiceUrl;
    @Value("${partner.port}")
    private int partnerServicePort;
    @Value("${partner.quoteEndpoint}")
    private String partnerServiceQuoteEndpoint;

    private Vertx vertx;
    private Gson gson;

    @PostConstruct
    public void setup() {
        vertx = Vertx.vertx();
        gson = new Gson();
        start();
    }

    public void start() {
        startClient(this.vertx);
    }

    private void startClient(Vertx vertx) {
        HttpClient client = vertx.createHttpClient();

        client.webSocket(partnerServicePort, partnerServiceUrl, "/"+partnerServiceQuoteEndpoint, (ar) -> {
            if (ar.succeeded()) {
                WebSocket ctx = ar.result();
                ctx.textMessageHandler((msg) -> {
                    QuoteDto quoteDto = gson.fromJson(msg, QuoteDto.class);
                    instrumentData.setQuote(quoteDto);
                    //System.out.println(quoteDto.getData().getIsin() + ": " + quoteDto.getData().getPrice());
                }).exceptionHandler((e) -> {
                    System.out.println("Restarting in 10 seconds");
                    client.close();
                    vertx.setTimer(10 * 1000, (__) -> {
                        startClient(vertx);
                    });
                });
            } else {
                System.out.println(ar.cause());
            }
        });
    }

}
