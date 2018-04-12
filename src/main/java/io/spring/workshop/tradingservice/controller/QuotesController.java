package io.spring.workshop.tradingservice.controller;

import io.spring.workshop.tradingservice.Quote;
import io.spring.workshop.tradingservice.client.QuotesClient;
import io.spring.workshop.tradingservice.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by prashastsaxena on 4/11/18.
 */

@RestController
public class QuotesController {

    @Autowired
    private QuotesClient quotesClient;

    @Autowired
    private QuoteRepository quoteRepository;

    private List<Quote> quotes = new ArrayList<>();


    @GetMapping(value = "/quotes/feed", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Quote> streamQuotes(){

        Flux<Quote> quotesFeed = quotesClient.quotesFeed();

        //optional block of code just to demonstrate that if we want to only persist DELL or CTXS stock only
        quotesFeed.subscribe(new Consumer<Quote>() {
            @Override
            public void accept(Quote quote) {
                if(quote.getTicker().equalsIgnoreCase("DELL") ||
                        quote.getTicker().equalsIgnoreCase("CTXS")){
                    System.out.println(quote);
                    quotes.add(quote);
                }
            }
        });


        quoteRepository.saveAll(quotesFeed).subscribe();
        //quotesFeed.map(quote -> quoteRepository.insert(quote));


        return quotesFeed;
    }

    @GetMapping(value = "/quotes/received", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Quote> getSavedQuotes(){
        return quotes;
    }


    @GetMapping(value = "/quote/{ticker}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Quote> getQuoteByTicker(@PathVariable("ticker") String ticker){
        return quoteRepository.findByTicker(ticker);
    }
}
