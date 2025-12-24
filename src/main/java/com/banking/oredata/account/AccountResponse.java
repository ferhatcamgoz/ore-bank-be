package com.banking.oredata.account;

import com.banking.oredata.base.BaseResponseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountResponse extends BaseResponseModel {
    private String name;
    private String number;
    private BigDecimal balance;
    private UUID userId;

    public AccountResponse(AccountModel entity) {
        super(entity);
        this.name = entity.getName();
        this.number = entity.getNumber();
        this.balance = entity.getBalance();
        this.userId = entity.getToUserId();
    }
}
