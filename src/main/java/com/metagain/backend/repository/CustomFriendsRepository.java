package com.metagain.backend.repository;


import com.metagain.backend.model.Friends;
import com.metagain.backend.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public class CustomFriendsRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Friends> findFriendsByProfile(Profile profile) {
        TypedQuery<Friends> query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.profile1=:profile OR f.profile2=:profile", Friends.class);
        query.setParameter("profile", profile);
        return query.getResultList();
    }

    public boolean exists(Profile profile1, Profile profile2) {
        try {
            findFriendsByBoth(profile1, profile2);
            return true;
        } catch(NoResultException e) {
            return false;
        }
    }

    public Friends findFriendsByBoth(Profile profile1, Profile profile2) {
        TypedQuery<Friends> query = entityManager.createQuery("SELECT f FROM Friends f WHERE (f.profile1=:profileone AND f.profile2=:profiletwo) OR (f.profile1=:profiletwo AND f.profile2=:profileone)", Friends.class);
        query.setParameter("profileone", profile1);
        query.setParameter("profiletwo", profile2);
        return query.getSingleResult();
    }

    public Friends findByIdAndProfile(UUID id, Profile profile) {
        TypedQuery<Friends> query = entityManager.createQuery("SELECT f FROM Friends f WHERE id=:uid AND (f.profile1=:profile OR f.profile2=:profile)", Friends.class);
        query.setParameter("profile", profile);
        query.setParameter("uid", id);
        return query.getSingleResult();
    }
}
