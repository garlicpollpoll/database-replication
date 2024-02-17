package com.replication.application;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private int age;
    private int balance;

    public Member(String name, int age, int balance) {
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    public Member updateMember(String name, int balance) {
        this.name = name;
        this.balance = balance;
        return this;
    }
}
