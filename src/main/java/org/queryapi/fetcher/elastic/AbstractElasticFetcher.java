package org.queryapi.fetcher.elastic;

import com.google.gson.Gson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.queryapi.dto.view.OrderViewDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractElasticFetcher {

    @Autowired
    protected RestHighLevelClient client;

    @Autowired
    protected Gson gson;

    protected <T> List<T> fetch(SearchResponse response, Class<T> cls) throws Exception {
        if (response.status() != RestStatus.OK) {
            throw new Exception("Request to elastic failed with status: " + response.status());
        }

        return List.of(response.getHits().getHits())
                .stream()
                .map(hit -> gson.fromJson(hit.getSourceAsString(), cls))
                .collect(Collectors.toList());
    }
}
