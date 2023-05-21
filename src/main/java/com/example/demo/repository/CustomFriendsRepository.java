package com.example.demo.repository;


import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CustomFriendsRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Friends> findFriendsByProfile(Profile profile) {
        TypedQuery<Friends> query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.profile1=:profile OR f.profile2=:profile", Friends.class);
        query.setParameter("profile", profile);
        return query.getResultList();
    }

}
