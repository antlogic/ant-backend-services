package com.ant.backendservices.repository.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocationEntity location;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CompanyEntity company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "transition_time_millis", nullable = false)
    private Long transitionTimeMillis;

    @Column(name = "orientation", nullable = false)
    private String orientation;
}
