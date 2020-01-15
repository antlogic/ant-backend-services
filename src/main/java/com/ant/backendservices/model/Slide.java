package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "slides", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class Slide extends UserDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Display display;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Location location;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;

    @OneToOne(cascade = CascadeType.DETACH)
    private Image image;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

}
