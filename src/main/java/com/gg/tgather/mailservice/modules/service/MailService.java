package com.gg.tgather.mailservice.modules.service;

import com.gg.tgather.commonservice.annotation.BaseServiceAnnotation;
import com.gg.tgather.commonservice.dto.account.AccountDto;
import com.gg.tgather.mailservice.modules.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;

@BaseServiceAnnotation
@RequiredArgsConstructor
public class MailService {

    private final AccountServiceClient accountServiceClient;

    public AccountDto getAccount(String accountId) {
        return accountServiceClient.getAccount(accountId);
    }

}
