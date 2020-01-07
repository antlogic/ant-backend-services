package com.ant.backendservices.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class CompanyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private Set<UserEntity> users;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "location_id")
//    private Set<LocationEntity> locations;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "display_id")
//    private Set<DisplayEntity> display;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;
}
