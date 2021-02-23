package org.queryapi.fetcher;

import graphql.schema.PropertyDataFetcher;
import org.queryapi.dao.AccountDao;
import org.queryapi.dto.AccountDto;

public class AccountPropertyFetcher extends PropertyDataFetcher<AccountDto> {

    private final AccountDao accountDao;

    public AccountPropertyFetcher(String propertyName, AccountDao accountDao) {
        super(propertyName);
        this.accountDao = accountDao;
    }


}
