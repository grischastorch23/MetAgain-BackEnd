package com.example.demo.repository;

import com.example.demo.model.Meeting;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MeetingRepository extends CrudRepository<Meeting, UUID> {

}
