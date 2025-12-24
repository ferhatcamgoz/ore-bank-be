package com.banking.oredata.account;

import com.banking.oredata.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
    name = "accounts",
    indexes = {
        @Index(name = "idx_accounts_number", columnList = "number"),
        @Index(name = "idx_accounts_to_user", columnList = "to_account_id")
    }
)@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AccountModel extends BaseModel {

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "to_account_id", nullable = false)
    private UUID toUserId;
}
