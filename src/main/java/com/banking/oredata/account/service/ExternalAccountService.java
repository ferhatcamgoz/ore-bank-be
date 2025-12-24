package com.banking.oredata.account.service;


import com.banking.oredata.account.AccountResponse;

import java.math.BigDecimal;
import java.util.UUID;


public interface ExternalAccountService {

    boolean existsByNumber(String number);

    boolean outMoney(String id, BigDecimal amount);
    boolean inMoney(String id, BigDecimal amount);

    UUID getAccountId(String accountNumber);

    String getAccountNumber(UUID accountId);

    AccountResponse getAccount(UUID accountId);
}
