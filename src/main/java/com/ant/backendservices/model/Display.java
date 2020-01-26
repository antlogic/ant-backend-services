package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "displays", uniqueConstraints = {
        @UniqueConstraint(columnNames = "device_id")
})
@Data
@EqualsAndHashCode(callSuper = false)
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

    @Column(name = "device_id", nullable = false, length = 40)
    private String deviceId;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "manufacturer", nullable = false, length = 100)
    private String manufacturer;

    @Column(name = "is_landscape", nullable = false)
    private Boolean isLandscape;
}
