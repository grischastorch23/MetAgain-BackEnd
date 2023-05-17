package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "from_profile", "to_profile", "request_type" }) })
public class Request {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "from_profile", referencedColumnName = "id")
    private Profile fromProfile;

    @ManyToOne
    @JoinColumn(name = "to_profile", referencedColumnName = "id")
    private Profile toProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private RequestType requestType;
}
