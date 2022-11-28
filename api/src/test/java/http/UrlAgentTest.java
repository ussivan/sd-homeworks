package http;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.status;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.method;
import static com.xebialabs.restito.semantics.Condition.startsWithUri;

import static org.assertj.core.api.Assertions.*;


public class UrlAgentTest {
    private static final int PORT = 32453;
    private final UrlAgent urlAgent = new BaseUrlAgent();

    @Test
    public void readAsText() {
        withStubServer(s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/hello"))
                    .then(stringContent("world"));

            String result = urlAgent.queryUrl("http://localhost:" + PORT + "/hello");

            assertThat(result).isEqualTo("world\n");
        });
    }

    @Test
    public void readAsTextWithNotFoundError() {
        withStubServer(s -> {
            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/hello"))
                    .then(status(HttpStatus.NOT_FOUND_404));

            assertThatExceptionOfType(UncheckedIOException.class)
                    .isThrownBy(() -> urlAgent.queryUrl("http://localhost:" + PORT + "/hello"));
        });
    }

    private void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(UrlAgentTest.PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
