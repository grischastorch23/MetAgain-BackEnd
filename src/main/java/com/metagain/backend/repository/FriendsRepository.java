package com.metagain.backend.repository;

import com.metagain.backend.model.Friends;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FriendsRepository extends CrudRepository<Friends, UUID> {


}
