package com.ant.backendservices.repository.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "slides", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@Data
public class SlideEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private DisplayEntity displayEntity;

    @ManyToOne
    private LocationEntity location;

    @ManyToOne
    private CompanyEntity company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "image_url", length = 250)
    private String imageUrl;
}
