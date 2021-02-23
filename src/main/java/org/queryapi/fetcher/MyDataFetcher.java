package org.queryapi.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.function.Function;

public class MyDataFetcher<P, T> implements DataFetcher<T> {

    private final Function<P, T> mapper;

    public MyDataFetcher(Function<P, T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public T get(DataFetchingEnvironment environment) throws Exception {
        P source = environment.getSource();

        return mapper.apply(source);
    }
}
