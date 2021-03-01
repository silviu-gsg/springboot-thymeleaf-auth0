package com.gsg.demo.repository;

import com.gsg.demo.entity.JournalEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends CrudRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(String userId);
}
