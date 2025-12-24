package com.banking.oredata.account.service;

import com.banking.oredata.account.AccountResponse;
import com.banking.oredata.account.dto.CreateOrUpdateAccountRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface RestAccountService extends ExternalAccountService{
    AccountResponse createAccount(@Valid CreateOrUpdateAccountRequest request);

    Page<AccountResponse> searchAccounts(String number, String name, Pageable pageable);

    AccountResponse updateAccount(UUID id, @Valid CreateOrUpdateAccountRequest request);

    void deleteAccount(UUID id);

    AccountResponse getAccountDetails(UUID id);
}
