package org.queryapi.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.queryapi.dao.OrderDao;
import org.queryapi.dto.OrderDto;

public class OrderFetcher implements DataFetcher<OrderDto> {

    private final OrderDao orderDao;

    public OrderFetcher(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public OrderDto get(DataFetchingEnvironment environment) throws Exception {
        Long id = environment.getArgument("id");
        if (id == null) {
            return null;
        }
        return orderDao.getById(id);
    }
}
