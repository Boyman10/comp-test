package org.example.utility;

import org.example.entity.Company;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

public class ApiClient implements Flow.Subscriber<Company> {

    public static void getJson(URI uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .setHeader("User-Agent", "Java 11 HttpClient")
                .header("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        CompletableFuture<String> result = response.thenApply(HttpResponse::body);

        result.thenAccept(System.out::println).join();
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Company item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
