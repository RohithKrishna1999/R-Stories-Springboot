package com.Rtech.Media.Modals;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "dbo.Users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "workname")
    private String workName;

    @Column(name = "unlockkey")
    private String unlockKey;

    @Column(name = "email")
    private String email;

}
