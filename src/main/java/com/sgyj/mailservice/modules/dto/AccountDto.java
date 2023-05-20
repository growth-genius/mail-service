package com.sgyj.mailservice.modules.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AccountDto {

    @Email
    private String email;

}
