package api;

import org.junit.jupiter.api.Test;
import com.google.gson.JsonSyntaxException;

import static org.assertj.core.api.Assertions.*;

class SearchResponseParserTest {
    private final static String CORRECT_RESPONSE = "{\"response\": {\"total_count\": 618}}";

    private final static String INVALID_RESPONSE = "{jaaj?}";

    @Test
    public void parseResponse() {
        var parser = new SearchResponseParser();
        var resp = parser.parseResponse(CORRECT_RESPONSE);

        assertThat(resp.amount).isEqualTo(618);
    }

    @Test
    public void parseMalformedResponse() {
        var parser = new SearchResponseParser();
        assertThatExceptionOfType(JsonSyntaxException.class)
                .isThrownBy(() -> parser.parseResponse(INVALID_RESPONSE));
    }
}
