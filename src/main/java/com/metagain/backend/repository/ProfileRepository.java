package com.metagain.backend.repository;

import com.metagain.backend.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProfileRepository extends CrudRepository<Profile, UUID> {
    Profile findByUsername(String username);
}
