package com.banking.oredata.account;

import com.banking.oredata.base.BaseService;
import com.banking.oredata.account.dto.CreateOrUpdateAccountRequest;
import com.banking.oredata.account.service.RestAccountService;
import com.banking.oredata.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


@Service
class AccountService extends BaseService implements RestAccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean outMoney(String number, BigDecimal amount) {
        AccountModel accountModel = accountRepository.findByNumber(number);
        //only account owner
        accessControl(accountModel);
        if (accountModel.getBalance().compareTo(amount) < 0) {
            return false;
        }
        accountModel.setBalance(accountModel.getBalance().subtract(amount));
        accountRepository.save(accountModel);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean inMoney(String number, BigDecimal amount) {
        AccountModel accountModel = accountRepository.findByNumber(number);
        accountModel.setBalance(accountModel.getBalance().add(amount));
        accountRepository.save(accountModel);
        return true;
    }

    @Override
    public UUID getAccountId(String accountNumber) {
        return accountRepository.findByNumber(accountNumber).getId();
    }

    @Override
    public String getAccountNumber(UUID accountId) {
        AccountModel account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        return account.getNumber();
    }

    @Override
    public AccountResponse getAccount(UUID accountId) {
        AccountModel account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        accessControl(account);
        return new AccountResponse(account);
    }


    @Override
    public AccountResponse createAccount(CreateOrUpdateAccountRequest request) {

        AccountModel account = new AccountModel();
        account.setToUserId(currentUserId());
        account.setName(request.getName());
        account.setBalance(request.getBalance());
        account.setNumber(generateAccountNumber());

        account = accountRepository.save(account);
        return toAccountResponse(account);
    }

    @Override
    public Page<AccountResponse> searchAccounts(String number, String name, Pageable pageable) {

        String safeNumber = (number == null || number.isBlank()) ? "" : number;
        String safeName   = (name == null || name.isBlank())   ? "" : name;

        Page<AccountModel> accounts =
            accountRepository
                .findByToUserIdAndNumberContainingIgnoreCaseAndNameContainingIgnoreCase(
                    currentUserId(),
                    safeNumber,
                    safeName,
                    pageable
                );

        return accounts.map(this::toAccountResponse);
    }

    @Override
    public AccountResponse updateAccount(UUID id, CreateOrUpdateAccountRequest request) {
        AccountModel account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        accessControl(account);
        account.setName(request.getName());
        account = accountRepository.save(account);
        return toAccountResponse(account);
    }

    @Override
    public void deleteAccount(UUID id) {
        AccountModel account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        accessControl(account);
        accountRepository.delete(account);
    }

    @Override
    public AccountResponse getAccountDetails(UUID id) {
        AccountModel account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        accessControl(account);
        return toAccountResponse(account);
    }


    private AccountResponse toAccountResponse(AccountModel entity) {
        AccountResponse accountResponse = new AccountResponse(entity);
        return accountResponse;
    }

    private String generateAccountNumber() {
        long count = accountRepository.count() + 1;
        return "ACC-" + String.format("%06d", count);
    }

    private boolean accessControl(AccountModel account) {
        if (!account.getToUserId().equals(currentUserId())) {
            throw new AccessDeniedException("Access denied");
        }
        return true;
    }
}
