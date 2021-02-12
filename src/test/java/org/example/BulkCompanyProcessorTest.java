package org.example;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.entities.properties.HttpApiProperties;
import org.example.utilities.StatsApi;
import org.example.utilities.http.HttpApiClient;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.File;
import java.io.IOException;

public class BulkCompanyProcessorTest {

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void bench_run_queue_5() {
        MockWebServer mockWebServer = new MockWebServer();

        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{}")
                .setResponseCode(200));

        StatsApi api = new StatsApi();

        HttpApiProperties properties = new HttpApiProperties();
        properties.url = mockWebServer.url("/").uri();
        properties.limit = 100;
        properties.queueSize = 2;
        properties.key = "";

        HttpApiClient client = new HttpApiClient(properties, api);
        BulkCompanyProcessor parser = new BulkCompanyProcessor(client, properties.limit);
        parser.run(new File("src/test/resources/companies.json"));
    }
}