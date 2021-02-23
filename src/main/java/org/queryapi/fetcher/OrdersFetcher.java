package org.queryapi.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.queryapi.dao.OrderDao;
import org.queryapi.dto.OrderDto;

import java.util.List;

public class OrdersFetcher implements DataFetcher<List<OrderDto>> {

    private final OrderDao orderDao;

    public OrdersFetcher(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<OrderDto> get(DataFetchingEnvironment environment) throws Exception {
        return orderDao.getAll();
    }
}
