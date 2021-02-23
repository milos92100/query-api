package org.queryapi.fetcher.elastic;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.queryapi.dto.view.OrderViewDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrdersByQueryElasticFetcher extends AbstractElasticFetcher implements DataFetcher<List<OrderViewDto>> {

    @Override
    public List<OrderViewDto> get(DataFetchingEnvironment environment) throws Exception {
        var query = (String) environment.getArgumentOrDefault("query", "");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.queryStringQuery(query).defaultField("*").analyzeWildcard(true));
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        sourceBuilder.query(boolQueryBuilder);


        SearchRequest searchRequest = new SearchRequest("orders");
        searchRequest.source(sourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        return fetch(response, OrderViewDto.class);
    }
}
