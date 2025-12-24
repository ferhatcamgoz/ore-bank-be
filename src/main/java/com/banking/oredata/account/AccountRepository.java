package com.banking.oredata.account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface AccountRepository extends JpaRepository<AccountModel, UUID> {

    AccountModel findByNumber(String number);

    Page<AccountModel> findByToUserIdAndNumberContainingIgnoreCaseAndNameContainingIgnoreCase(
        UUID toUserId,
        String number,
        String name,
        Pageable pageable
    );

    boolean existsByNumber(String number);
}
