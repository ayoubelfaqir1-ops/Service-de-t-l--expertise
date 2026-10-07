package com.teleexpertise.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    private Long id;

    public Consultation(){}

    public Long getId(){
        return id;
    }
}
