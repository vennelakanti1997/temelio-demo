package com.temelio.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "foundation")
public class Foundation extends CommonAttributes{

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "id")
    private List<NonProfit> nonProfits;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<EmailTemplates> emailTemplates;
}
