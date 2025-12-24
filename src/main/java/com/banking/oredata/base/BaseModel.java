package com.banking.oredata.base;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)",updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //for fast db index
    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UuidCreator.getTimeOrderedEpoch(); // UUIDv7
        }
    }
}
