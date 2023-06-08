package com.metagain.backend.repository;

import com.metagain.backend.model.Meeting;
import com.metagain.backend.model.Profile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class CustomMeetingRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Meeting> getMeetingsForProfile(Profile profile) {
        TypedQuery<Meeting> query = entityManager.createQuery("SELECT m FROM Meeting m WHERE m.profile1=:profileid OR m.profile2=:profileid", Meeting.class);
        query.setParameter("profileid", profile);
        return query.getResultList();
    }

    public Meeting findByIdAndProfile(UUID id, Profile profile) {
        TypedQuery<Meeting> query = entityManager.createQuery("SELECT m FROM Meeting m WHERE m.id=:uid AND (m.profile1=:profileid OR m.profile2=:profileid)", Meeting.class);
        query.setParameter("uid", id);
        query.setParameter("profileid", profile);
        return query.getSingleResult();
    }

}
