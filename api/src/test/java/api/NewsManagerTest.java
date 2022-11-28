package api;


import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class NewsManagerTest {

    private NewsManager newsManager;

    @Mock
    private VkClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newsManager = new NewsManager(client);
    }

    @Test
    public void testSearchNewsFeedSingle() {
        var start = Instant.ofEpochSecond(3600);
        when(client.search(eq("#test"), eq(Instant.ofEpochSecond(0)), eq(Instant.ofEpochSecond(3600))))
                .thenReturn(new SearchResponse(50));

        List<Long> stats = newsManager.getStatsForHashtag("#test", start, 1);

        verify(client, times(1))
                .search(any(), any(), any());
        assertThat(stats).isEqualTo(List.of(50L));
    }

    @Test
    public void testSearchNewsFeedMultiple() {
        var start = Instant.now();
        when(client.search(eq("#test"), any(), any()))
                .thenReturn(new SearchResponse(1),
                        new SearchResponse(2),
                        new SearchResponse(3),
                        new SearchResponse(4),
                        new SearchResponse(5));

        List<Long> stats = newsManager.getStatsForHashtag("#test", start, 5);

        verify(client, times(5)).search(any(), any(), any());
        assertThat(stats).isEqualTo(List.of(1L, 2L, 3L, 4L, 5L));
    }
}
