package com.metagain.backend.repository;

import com.metagain.backend.model.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {


}
