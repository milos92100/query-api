package org.queryapi.dao;

import org.queryapi.dto.OrderDto;

import java.util.List;

public interface OrderDao {
    List<OrderDto> getAll();

    OrderDto getById(long id);

}
