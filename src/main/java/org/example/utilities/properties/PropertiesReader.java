package org.example.utilities.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.example.entities.properties.HttpApiProperties;

import java.io.File;
import java.io.IOException;

public class PropertiesReader {

    public static HttpApiProperties loadProp() throws IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File("src/main/resources/application.yml"), HttpApiProperties.class);
    }
}

