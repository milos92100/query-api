package org.queryapi.fetcher.elastic;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.queryapi.dto.view.OrderViewDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersElasticFetcher extends AbstractElasticFetcher implements DataFetcher<List<OrderViewDto>> {

    @Override
    public List<OrderViewDto> get(DataFetchingEnvironment environment) throws Exception {
        SearchRequest searchRequest = new SearchRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        searchRequest.source(searchSourceBuilder);

        var searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        return fetch(searchResponse, OrderViewDto.class);
    }
}
