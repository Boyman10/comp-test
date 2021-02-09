package org.example.utilities.json;

import org.example.entities.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class JsonParserTest {

    @Test
    void test_server_parse() throws IOException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("server.json").toURI());
        String content = Files.readString(path);

        Server server = JsonParser.parseServer(content);

        Assertions.assertEquals(server.country, "United States");
    }
}