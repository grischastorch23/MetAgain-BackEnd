package com.example.demo.repository;

import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import com.example.demo.model.Restriction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRestrictionRepository {

    @Autowired
    private EntityManager entityManager;

    public Restriction findByBothProfiles(Profile fromProfile, Profile toProfile) {
        TypedQuery<Restriction> query = entityManager.createQuery("SELECT r FROM Restriction r WHERE r.fromProfile=:fid AND r.toProfile=:tid", Restriction.class);
        query.setParameter("fid", fromProfile.getId());
        query.setParameter("tid", toProfile.getId());
        return query.getSingleResult();
    }

}
