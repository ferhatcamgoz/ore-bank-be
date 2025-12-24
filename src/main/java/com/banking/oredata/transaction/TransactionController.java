package com.banking.oredata.transaction;

import com.banking.oredata.base.BaseApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction Management", description = "Transaction operations")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity transfer(@Valid @RequestBody TransferRequest request) {
        TransactionResponse response = transactionService.transfer(request);
        return ResponseEntity.ok(new BaseApiResponse(true, "Transfer successfully", response));
    }

    @GetMapping("/account/{id}")
    public ResponseEntity getTransactionsOfAccount(@PathVariable("id") UUID accountId) {

        List<TransactionResponse> transactions = transactionService.getHistory(accountId);
        return ResponseEntity.ok(new BaseApiResponse(true, "Transaction history successfully", transactions));
    }
}
