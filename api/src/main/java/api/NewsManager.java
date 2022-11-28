package api;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class NewsManager {

    private final VkClient client;

    public NewsManager(VkClient client) {
        this.client = client;
    }

    public List<Long> getStatsForHashtag(String hashtag, Instant from, int hoursBack) {
        var res = new ArrayList<Long>();
        for (long i = hoursBack - 1; i >= 0; i--) {
            Instant endTime = from.minus(Duration.ofHours(i));
            Instant startTime = endTime.minus(Duration.ofHours(1));
            SearchResponse response = client.search(hashtag, startTime, endTime);
            res.add(response.amount);
        }
        return res;
    }
}
