package com.metagain.backend.model;

import com.metagain.backend.model.types.RequestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "from_profile", "to_profile", "request_type" }) })
public class Request {

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
    @Column(name = "request_type")
    private RequestType requestType;

    private int radius;
}
