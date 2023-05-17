package com.example.demo.repository;


import com.example.demo.model.Friends;
import com.example.demo.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CustomFriendsRepository {

    private final EntityManager entityManager;

    public Friends findFriendsByBothIDs(UUID id1, UUID id2) {
        TypedQuery<Friends> query = entityManager.createQuery("SELECT f FROM Friends f WHERE f.profile1=:id1 AND f.profile2=:id2", Friends.class);
        query.setParameter("id1", id1);
        query.setParameter("id2", id2);
        return query.getSingleResult();
    }

}
