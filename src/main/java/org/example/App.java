package org.example;

import org.example.utilities.http.ApiClient;
import org.example.utilities.json.BulkJsonParser;
import org.example.utilities.properties.ApiInfo;
import org.example.utilities.properties.PropertiesReader;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {

        ApiInfo info = PropertiesReader.loadProp();
        ApiClient client = new ApiClient(info);

        BulkJsonParser parser = new BulkJsonParser(client);
        parser.run(new File("src/main/resources/companies.json"));
    }
}
