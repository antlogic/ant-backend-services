package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.DateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "companies")
@Data
public class Company extends DateAudit {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public Company setAddress(String address) {
        this.address = address;
        return this;
    }
}
