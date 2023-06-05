package com.metagain.backend.repository;

import com.metagain.backend.model.Meeting;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MeetingRepository extends CrudRepository<Meeting, UUID> {

}
