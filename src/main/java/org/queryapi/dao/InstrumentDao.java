package org.queryapi.dao;

import org.queryapi.dto.InstrumentDto;

public interface InstrumentDao {
    InstrumentDto getById(long id);
}
