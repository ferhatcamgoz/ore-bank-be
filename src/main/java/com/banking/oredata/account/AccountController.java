package com.banking.oredata.account;

import com.banking.oredata.base.BaseApiResponse;
import com.banking.oredata.account.dto.CreateOrUpdateAccountRequest;
import com.banking.oredata.account.service.RestAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management")
public class AccountController {

    private final RestAccountService accountService;

    @PostMapping
    public ResponseEntity createAccount(@RequestBody CreateOrUpdateAccountRequest request) {

        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.ok(new BaseApiResponse(true, "Account created successfully", response));
    }

    @PostMapping("/search")
    public ResponseEntity searchAccounts(
        @RequestParam(required = false, defaultValue = "") String number,
        @RequestParam(required = false, defaultValue = "") String name, Pageable pageable) {

        Page<AccountResponse> accounts = accountService.searchAccounts(number, name, pageable);
        return ResponseEntity.ok(new BaseApiResponse(true, "Account searched successfully", accounts));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAccount(@PathVariable UUID id,
                                        @Valid @RequestBody CreateOrUpdateAccountRequest request) {
        AccountResponse updated = accountService.updateAccount(id, request);
        return ResponseEntity.ok(new BaseApiResponse(true, "Account updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete account",
        description = "Deletes account for the authenticated user",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new BaseApiResponse(true, "Account deleted successfully", null));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get account details",
        description = "Get account details for the authenticated user",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity getAccountDetails(@PathVariable UUID id) {
        AccountResponse response = accountService.getAccountDetails(id);
        return ResponseEntity.ok(new BaseApiResponse(true, "Account details found successfully", response));
    }

}
