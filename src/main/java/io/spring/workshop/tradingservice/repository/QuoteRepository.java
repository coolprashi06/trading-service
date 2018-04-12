package io.spring.workshop.tradingservice.repository;

import io.spring.workshop.tradingservice.Quote;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Created by prashastsaxena on 4/12/18.
 */

@Repository
public interface QuoteRepository extends ReactiveMongoRepository<Quote, String>{

    Flux<Quote> findByTicker(String ticker);
}
