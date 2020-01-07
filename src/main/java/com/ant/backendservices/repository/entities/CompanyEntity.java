package com.ant.backendservices.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "companies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;
}
