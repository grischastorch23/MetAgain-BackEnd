package com.example.demo.repository;

import com.example.demo.model.Friends;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FriendsRepository extends CrudRepository<Friends, UUID> {


}