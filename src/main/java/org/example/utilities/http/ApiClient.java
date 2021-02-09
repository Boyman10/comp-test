package org.example.utilities.http;

import org.example.entities.Company;
import org.example.entities.Server;
import org.example.utilities.ParserCompanySubscriber;
import org.example.utilities.json.BulkJsonParser;
import org.example.utilities.json.JsonParser;
import org.example.utilities.properties.ApiInfo;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ApiClient implements ParserCompanySubscriber {

    private static final Logger L = LoggerFactory.getLogger(BulkJsonParser.class);
    private static final long REQUEST_VOLUME = 2;
    private final String key;
    private final URI url;
    private final HttpClient client;
    private Subscription subscription;

    private List<CompletableFuture<Void>> futures;

    public ApiClient(ApiInfo info) {
        this.key = info.key;
        this.url = info.url;
        this.client = HttpClient.newHttpClient();
        futures = new ArrayList<>();
    }

    public void getJson(URI uri, Company company) throws ExecutionException, InterruptedException {

        L.info("API call to send ");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .setHeader("User-Agent", "Java 11 HttpClient")
                .header("Content-Type", "application/json")
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        handleData(company, response);
    }

    private void handleData(Company company, CompletableFuture<HttpResponse<String>> response) {

        CompletableFuture<String> result = response.thenApply(HttpResponse::body);
        CompletableFuture<Server> server = result.thenApply(JsonParser::parseServer);
        CompletableFuture<Company> comp = server.thenApply(s -> {
            company.server = s;
            return company;
        });

        futures.add(comp.thenAccept(this::printResult));

        L.info("API call sent");
    }

    protected void printResult(Company company) {
        L.info(company.toString());
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        L.info("Subscribed by publisher");
        this.subscription = subscription;
        // control the flow here 1 by 1
        subscription.request(REQUEST_VOLUME);
    }

    @Override
    public void onNext(Company company) {
        try {
            URI uri = new URI(this.url + company.getDomainName() + "?access_key=" + this.key);
            L.info("About to fetch data from the API ; " + uri);
            this.getJson(uri, company);
        } catch (URISyntaxException e) {
            L.error("malformed URI ", e);
        } catch (InterruptedException | ExecutionException e) {
            L.error("Exception occurred ", e);
        }

        subscription.request(REQUEST_VOLUME);
    }

    @Override
    public void onError(Throwable throwable) {
        L.error("Got an error here : {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        L.info("Job completed");
    }

    @Override
    public List<CompletableFuture<Void>> getFutures() {
        return this.futures;
    }
}
