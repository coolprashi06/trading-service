package io.spring.workshop.tradingservice.client;

import io.spring.workshop.tradingservice.Quote;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Created by prashastsaxena on 4/11/18.
 */

@Component
public class QuotesClient {

    private WebClient webClient;

    public Flux<Quote> quotesFeed(){
        webClient = WebClient.builder().baseUrl("http://localhost:8081/quotes").build();
        return webClient.get()
                .retrieve()
                .bodyToFlux(Quote.class);

    }
}
