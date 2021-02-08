package org.example.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.example.entity.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.SubmissionPublisher;


public class JsonParser extends SubmissionPublisher<Company> {

    private static final Logger L = LoggerFactory.getLogger(JsonParser.class);

    public void parseJson(File file) throws UnsupportedEncodingException, FileNotFoundException {
        try {
            InputStream stream = new FileInputStream(file);
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Float.class, new MoneyDeserializer());
            Gson gson = builder.create();

            // Read file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                Company company = gson.fromJson(reader, Company.class);
                System.out.println(company); // SEND TO QUEUE

                this.submit(company);
            }
            reader.endArray();
            reader.close();

        } catch (UnsupportedEncodingException ex) {

            L.error("An error occurred", ex);

        } catch (IOException ex) {
            L.error("An IO Exception occurred", ex);
        }
    }
}
