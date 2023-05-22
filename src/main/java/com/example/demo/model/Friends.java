package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "profile1_id", "profile2_id" }) })
public class Friends {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "profile1_id", referencedColumnName = "id")
    private Profile profile1;

    @ManyToOne
    @JoinColumn(name = "profile2_id", referencedColumnName = "id")
    private Profile profile2;

    private int radius;

    private boolean inRadius;
}
