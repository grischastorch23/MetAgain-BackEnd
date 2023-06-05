package com.metagain.backend.repository;

import com.metagain.backend.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class CustomProfileRepository {

    @Autowired
    private EntityManager entityManager;

    public Profile findProfileByUsername(String username) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT p FROM Profile p WHERE p.username=:username", Profile.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Profile findByNameAndId(UUID id, String username) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT p FROM Profile p WHERE p.username=:username AND p.id=:uid", Profile.class);
        query.setParameter("username", username);
        query.setParameter("uid", id);
        return query.getSingleResult();
    }

}
