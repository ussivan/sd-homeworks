package api;

import com.xebialabs.restito.server.StubServer;
import http.BaseUrlAgent;
import org.glassfish.grizzly.http.Method;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.builder.verify.VerifyHttp.verifyHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

class UrlAgentIntegrationTest {
    private static final int PORT = 32453;

    @Test
    public void readAsText() {
        withStubServer(s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method"))
                    .then(stringContent("{\"response\": {\"total_count\": 618}\n}"));
            SearchResponse result = new VkUrlClient("http://localhost:" + PORT, new BaseUrlAgent())
                    .search("#test",
                            Instant.ofEpochSecond(100),
                            Instant.ofEpochSecond(200));

            assertThat(result.amount).isEqualTo(618);
            verifyHttp(s).once(
                    method(Method.GET),
                    uri("/method/newsfeed.search"),
                    parameter("q", "#test"),
                    parameter("start_time", "100"),
                    parameter("end_time", "200"));
        });
    }

    private void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(UrlAgentIntegrationTest.PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
