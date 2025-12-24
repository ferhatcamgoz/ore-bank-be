package com.banking.oredata.transaction;

import com.banking.oredata.account.AccountResponse;
import com.banking.oredata.account.service.ExternalAccountService;
import com.banking.oredata.base.BaseResponseModel;
import com.banking.oredata.base.BaseService;
import com.banking.oredata.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService extends BaseService {

    private final ExternalAccountService accountService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        if(request.getSendingAccountNumber().equals(request.getReceivingAccountNumber())){
            throw new RuntimeException("Cannot transfer between same accounts");
        }

        boolean sendingAccount = accountService.existsByNumber(request.getSendingAccountNumber());
        if (sendingAccount == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }

        boolean receivingAccount = accountService.existsByNumber(request.getReceivingAccountNumber());
        if (receivingAccount == false) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        TransactionModel transaction = new TransactionModel();

        if (accountService.outMoney(request.getSendingAccountNumber(), request.getAmount())){
            accountService.inMoney(request.getReceivingAccountNumber(), request.getAmount());
            transaction.setStatus(TransactionStatus.SUCCESS);
        }else {
            transaction.setStatus(TransactionStatus.FAILED);
        }


        transaction.setFromAccountId(accountService.getAccountId(request.getSendingAccountNumber()));
        transaction.setToAccountId(accountService.getAccountId(request.getReceivingAccountNumber()));
        transaction.setAmount(request.getAmount());
        transaction.setStatus(TransactionStatus.SUCCESS);

        transactionRepository.save(transaction);

        return toTransactionResponse(transaction);
    }

    public List<TransactionResponse> getHistory(UUID accountId) {
        //auth control
        accountService.getAccount(accountId);

        List<TransactionModel> transactions = transactionRepository.findByAccountId(accountId);

        return transactions.stream()
            .map(this::toTransactionResponse)
            .collect(Collectors.toList());
    }




    public TransactionResponse toTransactionResponse(TransactionModel transaction) {
        TransactionResponse dto = new TransactionResponse();
        dto.setFromAccountNumber(accountService.getAccountNumber(transaction.getFromAccountId()));
        dto.setToAccountNumber(accountService.getAccountNumber(transaction.getToAccountId()));
        dto.setFromAccountId(transaction.getFromAccountId());
        dto.setToAccountId(transaction.getToAccountId());
        dto.setAmount(transaction.getAmount());
        dto.setStatus(transaction.getStatus().name());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}
