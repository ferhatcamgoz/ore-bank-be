package com.banking.oredata.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrUpdateAccountRequest {

    @NotBlank
    private String name;
    @NotBlank
    private BigDecimal balance;

}
