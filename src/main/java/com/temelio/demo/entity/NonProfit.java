package com.temelio.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "non_profit")
public class NonProfit extends CommonAttributes{


    @Column(name ="email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "is_active", nullable = false)
    @ColumnDefault(value = "true")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "foundation_id", nullable = false)
    private Foundation foundation;
}
