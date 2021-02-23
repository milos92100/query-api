package org.queryapi;

import com.google.gson.Gson;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.queryapi.dao.AccountDao;
import org.queryapi.dao.InstrumentDao;
import org.queryapi.dao.OrderDao;
import org.queryapi.dao.mock.AccountMockDao;
import org.queryapi.dao.mock.InstrumentMockDao;
import org.queryapi.dao.mock.OrderMockDao;
import org.queryapi.dto.AccountDto;
import org.queryapi.dto.InstrumentDto;
import org.queryapi.dto.OrderDto;
import org.queryapi.fetcher.MyDataFetcher;
import org.queryapi.fetcher.OrdersFetcher;
import org.queryapi.fetcher.elastic.OrdersElasticFetcher;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;


public class QueryApiServer {

    private static final Logger LOGGER = LogManager.getLogger(QueryApiServer.class);

    public void start() {

        Properties properties;
        try {
            var propertiesStream = Objects.requireNonNull( //
                    QueryApiServer.class.getClassLoader().getResourceAsStream("application.properties") //
            );
            properties = new Properties();
            properties.load(propertiesStream);

            var schema = createGraphQlSchema(properties);
            var graphQL = GraphQL.newGraphQL(schema).build();

            String query = "query { orders { id instrument{ id name } volume limit } }";

            ExecutionResult executionResult = graphQL.execute(query);

            Gson gson = new Gson();

            System.out.println(gson.toJson(executionResult.toSpecification()));

            SchemaPrinter printer = new SchemaPrinter();
            System.out.println(printer.print(schema));

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private GraphQLSchema createGraphQlSchema(Properties properties) {
        String path = String.valueOf(Path.of("schema.graphqls").toAbsolutePath());
        File schemaFile = new File(path);

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);

        // change wiring with implementation
        RuntimeWiring runtimeWiring = createElasticRuntimeWiring(properties);

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring createMockRuntimeWiring(Properties properties) {
        AccountDao accountDao = new AccountMockDao();
        InstrumentDao instrumentDao = new InstrumentMockDao();
        OrderDao orderDao = new OrderMockDao();

        return RuntimeWiring.newRuntimeWiring()
                .type("QueryType", builder ->
                        builder
                                .dataFetcher("orders", new OrdersFetcher(orderDao))
                )
                .type("Order", builder ->
                        builder
                                .dataFetcher("account", new MyDataFetcher<OrderDto, AccountDto>(x -> accountDao.getById(x.getAccountId())))
                                .dataFetcher("instrument", new MyDataFetcher<OrderDto, InstrumentDto>(x -> instrumentDao.getById(x.getInstrumentId())))
                )
                .build();
    }

    private RuntimeWiring createElasticRuntimeWiring(Properties properties) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost(
                        properties.getProperty("elastic.host"),
                        Integer.getInteger(properties.getProperty("elastic.port")),
                        properties.getProperty("elastic.scheme")
                )
        ));

        return RuntimeWiring.newRuntimeWiring()
                .type("QueryType", builder ->
                        builder
                                .dataFetcher("orders", new OrdersElasticFetcher())
                )
                .build();
    }
}
