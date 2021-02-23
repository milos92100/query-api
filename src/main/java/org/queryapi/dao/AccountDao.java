package org.queryapi.dao;

import org.queryapi.dto.AccountDto;

public interface AccountDao {
    AccountDto getById(long id);
}
