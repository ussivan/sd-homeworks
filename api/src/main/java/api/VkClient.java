package api;

import java.time.Instant;

public interface VkClient {
    SearchResponse search(String query, Instant start, Instant end);
}
