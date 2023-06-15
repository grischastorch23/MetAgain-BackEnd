package com.metagain.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "profile_1", "profile_2"}) })
public class Meeting {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profile_1", referencedColumnName = "id")
    private Profile profile1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "profile_2", referencedColumnName = "id")
    private Profile profile2;

    private double[] meetingPoint;

}
