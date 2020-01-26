package com.ant.backendservices.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pair_device", uniqueConstraints = {
        @UniqueConstraint(columnNames = "pair_code")
})
@Data
public class PairDevice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pair_code", nullable = false, length = 6)
    private String pairCode;

    @Column(name = "paired_status", nullable = false)
    private Boolean pairedStatus;

    @Column(name = "display_id", nullable = true)
    private Long displayId;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "device_id", nullable = false, length = 100)
    private String deviceId;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "manufacturer", nullable = false, length = 100)
    private String manufacturer;

    @Column(name = "is_landscape", nullable = false)
    private Boolean isLandscape;
}
