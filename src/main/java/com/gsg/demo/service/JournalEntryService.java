package com.gsg.demo.service;

import com.gsg.demo.dto.JournalEntryDto;
import com.gsg.demo.entity.JournalEntry;
import com.gsg.demo.repository.JournalEntryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class JournalEntryService {
    public static final String INVALID_JOURNAL_ENTRY_ID = "Invalid journal entry id: ";
    private final JournalEntryRepository journalEntryRepository;
    private final ModelMapper modelMapper;

    public List<JournalEntryDto> findEntriesForUserId(String userId) {
        List<JournalEntry> journalEntries = journalEntryRepository.findByUserId(userId);

        return modelMapper.map(journalEntries, new TypeToken<List<JournalEntryDto>>() {
        }.getType());
    }

    public JournalEntryDto create(JournalEntryDto journalEntryDto) {
        JournalEntry entity = modelMapper.map(journalEntryDto, JournalEntry.class);
        entity.setTimestamp(LocalDateTime.now());
        return modelMapper.map(journalEntryRepository.save(entity), JournalEntryDto.class);
    }

    public JournalEntryDto findById(Long id) {
        JournalEntry entity = journalEntryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_JOURNAL_ENTRY_ID + id));
        return modelMapper.map(entity, JournalEntryDto.class);
    }

    public JournalEntryDto update(Long id, JournalEntryDto journalEntryDto) {
        JournalEntry entity = journalEntryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_JOURNAL_ENTRY_ID + id));
        entity.setTitle(journalEntryDto.getTitle());
        entity.setContent(journalEntryDto.getContent());
        return modelMapper.map(journalEntryRepository.save(entity), JournalEntryDto.class);
    }

    public void delete(Long id) {
        JournalEntry entity = journalEntryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(INVALID_JOURNAL_ENTRY_ID + id));
        journalEntryRepository.delete(entity);
    }

}
