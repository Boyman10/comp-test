package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import io.reactivex.rxjava3.core.Flowable;
import org.example.entities.Company;
import org.example.utilities.ParserCompanySubscriber;
import org.example.utilities.json.MoneyDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class BulkCompanyProcessor {

    private static final Logger L = LoggerFactory.getLogger(BulkCompanyProcessor.class);

    private final ParserCompanySubscriber subscriber;
    private final int limit;

    public BulkCompanyProcessor(ParserCompanySubscriber subscriber, int limit) {
        this.subscriber = subscriber;
        this.limit = limit;
    }

    public void run(File file) {
        try {
            InputStream stream = new FileInputStream(file);
            JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Double.class, new MoneyDeserializer());
            Gson gson = builder.create();

            L.info("About to read file {} in stream mode", file);
            long timelapse = System.currentTimeMillis();
            reader.beginArray();

            int i = 0;
            while (reader.hasNext()) {
                if (limit > 0 && i > limit) {
                    reader.skipValue();
                } else {
                    this.subscriber.getSemaphore().acquire();
                    Company company = gson.fromJson(reader, Company.class);
                    Flowable.just(company).subscribe(subscriber);
                }
                i++;
            }
            reader.endArray();
            reader.close();

            L.info("Went over the file ({} companies) in {} ms", i, System.currentTimeMillis() - timelapse);
            L.info("Now waiting for the subscriber to terminate");
            CompletableFuture.allOf(subscriber.getFutures().toArray(new CompletableFuture[subscriber.getFutures().size()])).join();

        } catch (UnsupportedEncodingException ex) {

            L.error("An error occurred", ex);

        } catch (IOException ex) {
            L.error("An IO Exception occurred ", ex);
        } catch (InterruptedException e) {
            L.error("Got interrupted ", e);
        }
    }
}
