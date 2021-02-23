package org.queryapi.fetcher.elastic;

import com.google.gson.Gson;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.queryapi.dto.view.OrderViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrdersElasticFetcher implements DataFetcher<List<OrderViewDto>> {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    Gson gson;

    @Override
    public List<OrderViewDto> get(DataFetchingEnvironment environment) throws Exception {
        SearchRequest searchRequest = new SearchRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        if (searchResponse.status() != RestStatus.OK) {
            throw new Exception("Request to elastic failed with status: " + searchResponse.status());
        }

        return List.of(searchResponse.getHits().getHits())
                .stream()
                .map(hit -> gson.fromJson(hit.getSourceAsString(), OrderViewDto.class))
                .collect(Collectors.toList());
    }
}
