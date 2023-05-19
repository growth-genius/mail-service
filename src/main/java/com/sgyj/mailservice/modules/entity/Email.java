package com.sgyj.mailservice.modules.entity;

import com.sgyj.mailservice.modules.common.UpdatedEntity;
import com.sgyj.mailservice.modules.entity.enums.EmailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Email extends UpdatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    private Long accountId;

    private EmailType emailType;

    private String content;

}
