package com.banking.oredata.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    @NotNull(message = "Sender account number cannot be null")
    @Schema(description = "ID of the account sending the money", example = "Main Account")
    private String sendingAccountNumber;
    @NotNull(message = "Recipient account number cannot be null")
    @Schema(description = "ID of the account receiving the money", example = "Dollar Account")
    private String receivingAccountNumber;
    @NotNull(message = "Amount cannot be null")
    @Schema(description = "Amount of money to be transferred", example = "1000")
    private BigDecimal amount;
}
