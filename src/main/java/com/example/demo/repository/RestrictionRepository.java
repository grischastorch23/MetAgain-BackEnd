package com.example.demo.repository;

import com.example.demo.model.Restriction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RestrictionRepository extends CrudRepository<Restriction, UUID> {

}
