package com.ant.backendservices.model;

import com.ant.backendservices.model.audit.UserDateAudit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
public class Image extends UserDateAudit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;
//
//    @ManyToOne(optional = true, cascade = CascadeType.REMOVE)
//    private Slide slide;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    @Column(name = "height")
    private Long height;

    @Column(name = "width")
    private Long width;

    // Size in bytes
    @Column(name = "size")
    private Long size;

}
