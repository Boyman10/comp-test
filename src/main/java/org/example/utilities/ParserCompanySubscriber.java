package org.example.utilities;

import org.example.entities.Company;
import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public interface ParserCompanySubscriber extends Subscriber<Company> {

    List<CompletableFuture<Void>> getFutures();

    void setSemaphore(Semaphore semaphore);
}
