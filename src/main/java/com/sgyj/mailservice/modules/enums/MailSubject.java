package com.sgyj.mailservice.modules.enums;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum MailSubject {
    VALID_AUTHENTICATION_ACCOUNT("TGather 회원가입 인증 메일", "validAuthenticationMail"), CONFIRM_JOIN_MEMBER("TravelGroup 가입 요청 메일", "confirmJoinMemberMail");


    private final String subject;

    private final String htmlCode;

    public String getSubject() {
        return subject;
    }

    public String getHtmlCode() {
        return htmlCode;
    }


}
