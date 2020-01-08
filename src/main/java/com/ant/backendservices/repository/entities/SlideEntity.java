package com.ant.backendservices.repository.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DisplayEntity displayEntity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LocationEntity location;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CompanyEntity company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "image_url", length = 250)
    private String imageUrl;
}
