package org.queryapi.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.queryapi.dao.InstrumentDao;
import org.queryapi.dto.InstrumentDto;

public class InstrumentFetcher implements DataFetcher<InstrumentDto> {

    private final InstrumentDao instrumentDao;

    public InstrumentFetcher(InstrumentDao instrumentDao) {
        this.instrumentDao = instrumentDao;
    }

    @Override
    public InstrumentDto get(DataFetchingEnvironment environment) throws Exception {
        Long id = environment.getArgument("id");

        if (id == null) {
            return null;
        }
        return instrumentDao.getById(id);
    }
}
