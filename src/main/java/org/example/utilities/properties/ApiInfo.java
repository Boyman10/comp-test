package org.example.utilities.properties;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiInfo {
    public URI url;
    public String key;

    public URI getFullUri(String queryParam) throws URISyntaxException {
        return new URI(url.getQuery() + queryParam + key);
    }
}
