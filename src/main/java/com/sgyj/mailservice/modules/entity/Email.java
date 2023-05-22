package com.sgyj.mailservice.modules.entity;

import com.sgyj.commonservice.dto.mail.EmailMessage;
import com.sgyj.commonservice.dto.mail.EmailType;
import com.sgyj.mailservice.modules.common.UpdatedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    private String accountId;

    private String emailAddress;

    private EmailType emailType;

    private String content;


    public static Email from(EmailMessage emailMessage) {
        Email email = new Email();
        email.accountId = emailMessage.getAccountId();
        email.emailAddress = emailMessage.getTo();
        email.emailType = emailMessage.getEmailType();
        email.content = emailMessage.getMessage();
        return email;
    }

}
