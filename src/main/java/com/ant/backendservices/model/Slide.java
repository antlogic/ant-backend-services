package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "slides", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class Slide extends UserDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Display> displays = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Location> locations = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private Company company;

    @OneToOne(cascade = CascadeType.DETACH)
    private Image image;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "transition_time_millis", nullable = false)
    private Long transitionTimeMillis;

}
