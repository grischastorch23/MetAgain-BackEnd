package com.metagain.backend.repository;

import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Restriction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomRestrictionRepository {

    @Autowired
    private EntityManager entityManager;

    public Restriction findByBothProfiles(Profile fromProfile, Profile toProfile) {
        TypedQuery<Restriction> query = entityManager.createQuery("SELECT r FROM Restriction r WHERE r.fromProfile=:fid AND r.toProfile=:tid", Restriction.class);
        query.setParameter("fid", fromProfile);
        query.setParameter("tid", toProfile);
        return query.getSingleResult();
    }

    public Restriction findByIdAndProfile(UUID id, Profile profile) {
        TypedQuery<Restriction> query = entityManager.createQuery("SELECT r FROM Restriction r WHERE r.fromProfile=:profileid AND r.id=:uid", Restriction.class);
        query.setParameter("profileid", profile);
        query.setParameter("uid", id);
        return query.getSingleResult();
    }

}
