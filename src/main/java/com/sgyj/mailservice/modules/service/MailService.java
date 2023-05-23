package com.sgyj.mailservice.modules.service;

import com.sgyj.commonservice.annotation.BaseServiceAnnotation;
import com.sgyj.commonservice.dto.account.AccountDto;
import com.sgyj.mailservice.modules.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class MailService {

    private final AccountServiceClient accountServiceClient;

    public AccountDto getAccount(String accountId) {
        return accountServiceClient.getAccount(accountId);
    }

}
