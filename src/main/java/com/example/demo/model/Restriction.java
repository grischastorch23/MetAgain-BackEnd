package com.example.demo.model;

import com.example.demo.model.types.RestrictionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "from_profile", "to_profile"}) })
public class Restriction {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "from_profile", referencedColumnName = "id")
    private Profile fromProfile;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "to_profile", referencedColumnName = "id")
    private Profile toProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "restriction_type")
    private RestrictionType restrictionType;
}
