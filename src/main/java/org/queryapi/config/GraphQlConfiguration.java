package org.queryapi.config;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.queryapi.fetcher.elastic.OrdersElasticFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Configuration
public class GraphQlConfiguration {

    private static final String SCHEMA = "schema.graphqls";

    @Autowired
    OrdersElasticFetcher ordersElasticFetcher;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GraphQL graphQL() throws URISyntaxException {

        return GraphQL.newGraphQL(createSchema()).build();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public GraphQLSchema graphQLSchema() throws URISyntaxException {
        return createSchema();
    }

    private GraphQLSchema createSchema() throws URISyntaxException {
        var schemaFile = loadSchemaFile();
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schemaFile);


        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type("QueryType", builder ->
                        builder
                                .dataFetcher("orders", ordersElasticFetcher)
                )
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private File loadSchemaFile() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(SCHEMA);
        assert resource != null;

        return new File(resource.toURI());
    }
}
