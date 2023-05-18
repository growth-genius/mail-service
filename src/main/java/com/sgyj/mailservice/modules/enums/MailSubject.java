package com.sgyj.mailservice.modules.enums;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum MailSubject {
    VALID_AUTHENTICATION_ACCOUNT("TGather 회원가입 인증 메일");

    private final String subject;

    public String getSubject() {
        return subject;
    }
}
