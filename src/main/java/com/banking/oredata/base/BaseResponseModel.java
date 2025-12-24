package com.banking.oredata.base;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseResponseModel {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BaseResponseModel(BaseModel baseModel) {
        this.id = baseModel.getId();
        this.createdAt = baseModel.getCreatedAt();
        this.updatedAt = baseModel.getUpdatedAt();
    }
}
