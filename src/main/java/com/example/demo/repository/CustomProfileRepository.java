package com.example.demo.repository;

import com.example.demo.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomProfileRepository {

    @Autowired
    private EntityManager entityManager;

    public Profile findProfileByUsername(String username) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT p FROM Profile p WHERE p.username=:username", Profile.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}
