package org.example.utilities;

import org.example.entities.Company;
import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ParserCompanySubscriber extends Subscriber<Company> {

    public List<CompletableFuture<Void>> getFutures();
}
