package org.example.utilities.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.example.entities.Company;
import org.example.utilities.properties.ApiInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

class ApiClientTest {

    MockWebServer mockWebServer = new MockWebServer();

    @Test
    public void test_getJson() throws ExecutionException, InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setBody("{}")
                .setResponseCode(200));

        ApiClient client = new ApiClient(new ApiInfo());
        client.getJson(mockWebServer.url("/").uri(), new Company());

        Assertions.assertFalse(client.getFutures().isEmpty());
    }
}