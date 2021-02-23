package org.queryapi.dao.mock;

import org.queryapi.dao.OrderDao;
import org.queryapi.dto.OrderDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMockDao implements OrderDao {
    private static final Map<Long, OrderDto> mocks = new HashMap<>() {{
        put(1L, new OrderDto(1L, 1L, 2L, 44D, 333.2));
        put(2L, new OrderDto(2L, 2L, 1L, 250D, 202D));
        put(3L, new OrderDto(3L, 3L, 3L, 1D, 99.99));
        put(4L, new OrderDto(4L, 4L, 4L, 3000D, 20.4));
        put(5L, new OrderDto(5L, 5L, 2L, 119D, 333.5));
        put(6L, new OrderDto(6L, 6L, 1L, 111D, 200D));
    }};

    @Override
    public List<OrderDto> getAll() {
        return new ArrayList<>(mocks.values());
    }

    @Override
    public OrderDto getById(long id) {
        return mocks.get(id);
    }
}
