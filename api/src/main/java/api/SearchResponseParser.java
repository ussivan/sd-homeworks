package api;

import com.google.gson.Gson;

import java.util.Map;

public class SearchResponseParser {
    @SuppressWarnings("rawtypes")
    public SearchResponse parseResponse(String json) {
        Map parsed = new Gson().fromJson(json, Map.class);
        Map response = (Map) parsed.get("response");
        Double totalCount = (Double) response.get("total_count");
        return new SearchResponse(totalCount.longValue());
    }
}
