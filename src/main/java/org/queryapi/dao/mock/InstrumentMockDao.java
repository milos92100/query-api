package org.queryapi.dao.mock;

import org.queryapi.dao.InstrumentDao;
import org.queryapi.dto.InstrumentDto;

import java.util.HashMap;
import java.util.Map;

public class InstrumentMockDao implements InstrumentDao {

    private static final Map<Long, InstrumentDto> mocks = new HashMap<>() {{
        put(1L, new InstrumentDto(1L, "BMW STOCK-1", "Eurex"));
        put(2L, new InstrumentDto(2L, "AUDI STOCK-31", "Eurex"));
        put(3L, new InstrumentDto(3L, "WV-23", "Eurex"));
        put(4L, new InstrumentDto(4L, "MERCEDES STOCK-99", "Eurex"));
    }};

    @Override
    public InstrumentDto getById(long id) {
        return mocks.get(id);
    }
}
