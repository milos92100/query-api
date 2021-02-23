package org.queryapi.controller;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.queryapi.request.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GraphQLController {
    private static final Logger LOGGER = LogManager.getLogger(GraphQLController.class);

    @Autowired
    GraphQL graphQL;

    @Autowired
    GraphQLSchema graphQLSchema;

    @PostMapping("/graphql")
    public ExecutionResult graphql(@RequestBody Query query) {
        LOGGER.info("query: {}", query.getQuery());
        return graphQL.execute(query.getQuery());
    }

    @GetMapping("/graphql")
    public String schema() {
        SchemaPrinter printer = new SchemaPrinter();
        return printer.print(graphQLSchema);
    }

}
