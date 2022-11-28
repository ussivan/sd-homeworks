package api;

import http.UrlAgent;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

public class VkUrlClient implements VkClient {

    private static final String API_KEY;
    private static final String SEARCH_STRING = "%s/method/newsfeed.search?q=%s&start_time=%d&end_time=%d&access_token=%s&v=5.131";

    static {
        try {
            API_KEY = Files.readString(Path.of("vk_access_token"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final SearchResponseParser parser;
    private final UrlAgent reader;
    private final String host;

    public VkUrlClient(String host, UrlAgent reader) {
        this.host = host;
        this.reader = reader;
        this.parser = new SearchResponseParser();
    }

    @Override
    public SearchResponse search(String query, Instant start, Instant end) {
        String response = reader.queryUrl(createSearchUrl(query, start.getEpochSecond(), end.getEpochSecond()));
        return parser.parseResponse(response);
    }

    private String createSearchUrl(String query, long start, long end) {
        return String.format(SEARCH_STRING, host, urlEncode(query), start, end, API_KEY);
    }

    private String urlEncode(String query) {
        return URLEncoder.encode(query, StandardCharsets.UTF_8);
    }
}
