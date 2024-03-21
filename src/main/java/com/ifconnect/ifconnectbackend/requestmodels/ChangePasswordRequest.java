package com.ifconnect.ifconnectbackend.requestmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    private int code;
    private String userEmail;
    private String newPassword;
    private String confirmationPassword;
}