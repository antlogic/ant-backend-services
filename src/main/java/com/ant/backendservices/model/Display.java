package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "displays")
@Data
public class Display extends UserDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Location location;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "transition_time_millis", nullable = false)
    private Long transitionTimeMillis;

    @Column(name = "orientation", nullable = false)
    private String orientation;
}
