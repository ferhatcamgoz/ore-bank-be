package com.banking.oredata.user.dto;

import com.banking.oredata.base.BaseResponseModel;
import com.banking.oredata.user.UserModel;
import lombok.Data;

@Data
public class UserResponse extends BaseResponseModel {

    private String username;
    private String email;

    public UserResponse(UserModel user) {
        super(user);
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
