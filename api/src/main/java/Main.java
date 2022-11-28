import api.VkUrlClient;
import api.NewsManager;
import http.BaseUrlAgent;

import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        NewsManager newsManager = new NewsManager(new VkUrlClient("https://api.vk.com", new BaseUrlAgent()));
        List<Long> res = newsManager.getStatsForHashtag("hello", Instant.now(), 24);
        System.out.println(res);
    }
}
