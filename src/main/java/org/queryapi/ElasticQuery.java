package org.queryapi;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.queryapi.dto.view.AccountViewDto;
import org.queryapi.dto.view.InstrumentViewDto;
import org.queryapi.dto.view.OrderViewDto;

import java.io.IOException;
import java.util.UUID;

public class ElasticQuery {

    private static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

        saveOrders(client);

        SearchRequest searchRequest = new SearchRequest("orders");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        searchResponse.getHits();

        client.close();

        System.out.println("Orders saved to elastic");
    }

    private static void saveOrders(RestHighLevelClient client) throws IOException {
        indexRecord(client, "orders", new OrderViewDto(1L, new AccountViewDto(1L, "Foo-USD", 1L), new InstrumentViewDto(1L, "BMW STOCK-1", "Eurex"), 44D, 333.2));
        indexRecord(client, "orders", new OrderViewDto(2L, new AccountViewDto(2L, "Bar-USD", 22L), new InstrumentViewDto(1L, "BMW STOCK-1", "Eurex"), 250D, 202D));
        indexRecord(client, "orders", new OrderViewDto(3L, new AccountViewDto(3L, "Credit Swiss-EUR", 15L), new InstrumentViewDto(3L, "WV-23", "Eurex"), 1D, 99.99));
        indexRecord(client, "orders", new OrderViewDto(4L, new AccountViewDto(4L, "Barclays-EUR", 33L), new InstrumentViewDto(4L, "MERCEDES STOCK-99", "Eurex"), 3000D, 20.4));
        indexRecord(client, "orders", new OrderViewDto(5L, new AccountViewDto(5L, "DZ-EUR", 33L), new InstrumentViewDto(2L, "AUDI STOCK-31", "Eurex"), 119D, 333.5));
        indexRecord(client, "orders", new OrderViewDto(6L, new AccountViewDto(6L, "NDS-EUR", 4L), new InstrumentViewDto(1L, "BMW STOCK-1", "Eurex"), 111D, 200D));
    }

    private static void indexRecord(RestHighLevelClient client, String index, Object record) throws IOException {
        IndexRequest saveEventRequest = new IndexRequest(index);
        saveEventRequest.id(UUID.randomUUID().toString());
        saveEventRequest.source(gson.toJson(record), XContentType.JSON);
        saveEventRequest.timeout(TimeValue.timeValueSeconds(10));
        saveEventRequest.opType(DocWriteRequest.OpType.CREATE);

        var response = client.index(saveEventRequest, RequestOptions.DEFAULT);
        if (response.status() != RestStatus.ACCEPTED && response.status() != RestStatus.CREATED) {
            System.err.println("Failed to store " + index + ": " + response.status());
        }
    }
}
