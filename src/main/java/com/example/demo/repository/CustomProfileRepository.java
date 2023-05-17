package com.example.demo.repository;

import com.example.demo.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomProfileRepository {

    private final EntityManager entityManager;

    public Profile findProfileByUsername(String username) {
        TypedQuery<Profile> query = entityManager.createQuery("SELECT p FROM Profile p WHERE p.username=:username", Profile.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}
