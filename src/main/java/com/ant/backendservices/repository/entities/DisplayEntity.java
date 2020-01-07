package com.ant.backendservices.repository.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "displays", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class DisplayEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private LocationEntity location;

    @ManyToOne
    private CompanyEntity company;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "slide_id")
//    private Set<SlideEntity> slides;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "transition_time_millis", nullable = false)
    private Long transitionTimeMillis;

    @Column(name = "orientation", nullable = false)
    private String orientation;
}
