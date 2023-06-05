package com.metagain.backend.repository;

import com.metagain.backend.model.Restriction;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RestrictionRepository extends CrudRepository<Restriction, UUID> {

}
