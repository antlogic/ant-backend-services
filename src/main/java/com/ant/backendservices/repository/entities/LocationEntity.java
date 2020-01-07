package com.ant.backendservices.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class LocationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CompanyEntity company;
//
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "display_id")
//    private Set<DisplayEntity> displays;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "phone_number", length = 11)
    private Long phone_number;

}
