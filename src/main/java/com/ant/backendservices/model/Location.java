package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Data
public class Location extends UserDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phone_number", length = 11)
    private Long phoneNumber;

}
