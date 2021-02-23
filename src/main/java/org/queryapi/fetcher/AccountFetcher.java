package org.queryapi.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.queryapi.dao.AccountDao;
import org.queryapi.dto.AccountDto;

import java.util.function.Function;

public class AccountFetcher<IN, OUT> implements DataFetcher<AccountDto> {

    private final AccountDao accountDao;
    private  Function<IN, OUT> propertyMapper;

    public AccountFetcher(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public AccountDto get(DataFetchingEnvironment environment) throws Exception {
        Long id = environment.getArgument("id");

        OUT a = propertyMapper.apply((IN) environment.getSource());

        if (id == null) {
            return null;
        }
        return accountDao.getById(id);
    }
}
