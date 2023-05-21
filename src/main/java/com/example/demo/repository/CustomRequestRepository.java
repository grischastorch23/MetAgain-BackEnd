package com.example.demo.repository;


import com.example.demo.model.Profile;
import com.example.demo.model.Request;
import jakarta.persistence.EntityManager;
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
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r WHERE r.id=:uid AND r.toProfile=:profileid", Request.class);
        query.setParameter("uid", id);
        query.setParameter("profileid", profile);
        return query.getSingleResult();
    }

    public List<Request> findRequestsForProfile(Profile profile) {
        TypedQuery<Request> query = entityManager.createQuery("SELECT r FROM Request r WHERE r.toProfile=:profileid", Request.class);
        query.setParameter("profileid", profile);
        return query.getResultList();
    }

}
