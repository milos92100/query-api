package org.queryapi.dao.mock;

import org.queryapi.dao.AccountDao;
import org.queryapi.dto.AccountDto;

import java.util.HashMap;
import java.util.Map;

public class AccountMockDao implements AccountDao {
    private static final Map<Long, AccountDto> mocks = new HashMap<>() {
        {
            put(1L, new AccountDto(1L, "Foo-USD", 1L));
            put(2L, new AccountDto(2L, "Bar-USD", 22L));
            put(3L, new AccountDto(3L, "Credit Swiss-EUR", 15L));
            put(4L, new AccountDto(4L, "Barclays-EUR", 33L));
            put(5L, new AccountDto(5L, "DZ-EUR", 33L));
            put(6L, new AccountDto(6L, "NDS-EUR", 4L));

        }
    };


    @Override
    public AccountDto getById(long id) {
        return mocks.get(id);
    }
}
