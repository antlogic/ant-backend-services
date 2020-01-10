package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Display display;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "image_url", length = 250)
    private String imageUrl;
}
