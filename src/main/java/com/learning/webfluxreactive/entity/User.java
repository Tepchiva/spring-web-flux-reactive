package com.learning.webfluxreactive.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("iam_user")
@Getter
@Setter
public class User {
    @Id
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String salt;
    private String password;
}
