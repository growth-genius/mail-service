package com.gg.mailservice.modules.service;

import com.gg.commonservice.annotation.BaseServiceAnnotation;
import com.gg.commonservice.dto.account.AccountDto;
import com.gg.mailservice.modules.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class MailService {

    private final AccountServiceClient accountServiceClient;

    public AccountDto getAccount(String accountId) {
        return accountServiceClient.getAccount(accountId);
    }

}
