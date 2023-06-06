package com.metagain.backend.repository;


import com.metagain.backend.model.Profile;
import com.metagain.backend.model.Request;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class CustomRequestRepository {

    @Autowired
    private EntityManager entityManager;

    public Request findRequestByIdAndProfile(UUID id, Profile profile) {
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r WHERE r.id=:uid AND r.toProfile.id=:profileId", Request.class);
        query.setParameter("uid", id);
        query.setParameter("profileId", profile.getId());
        return query.getSingleResult();
    }

    public List<Request> findRequestsForProfile(Profile profile) {
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r WHERE r.toProfile=:profileid", Request.class);
        query.setParameter("profileid", profile);
        return query.getResultList();
    }

    public boolean existsKindalike(Request request) {
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r WHERE r.fromProfile=:uid1 AND r.toProfile=:uid2", Request.class);
        query.setParameter("uid1", request.getToProfile());
        query.setParameter("uid2", request.getFromProfile());
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}
