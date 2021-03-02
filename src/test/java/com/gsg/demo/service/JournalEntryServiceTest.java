package com.gsg.demo.service;

import com.gsg.demo.dto.JournalEntryDto;
import com.gsg.demo.repository.JournalEntryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionSystemException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JournalEntryServiceTest {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    ModelMapper modelMapper;

    public static final String USER_ID = "123";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        journalEntryRepository.deleteAll();
    }

    @Test
    void create_successful() {
        JournalEntryDto dtoToCreate = getValidJournalEntryDto();

        JournalEntryDto createdDto = journalEntryService.create(dtoToCreate);

        assertThat(createdDto).isNotNull();
        assertThat(createdDto.getUserId()).isEqualTo(dtoToCreate.getUserId());
        assertThat(createdDto.getTitle()).isEqualTo(dtoToCreate.getTitle());
        assertThat(createdDto.getContent()).isEqualTo(dtoToCreate.getContent());
        assertThat(journalEntryRepository.count()).isEqualTo(1);
    }

    @Test
    void create_unsuccessful() {
        Exception exception = Assertions.assertThrows(TransactionSystemException.class, () -> {
            journalEntryService.create(getInvalidJournalEntryDto());
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).contains("Error while committing the transaction");
        assertThat(journalEntryRepository.count()).isZero();
    }

    @Test
    void findByUserId_successful() {
        journalEntryService.create(getValidJournalEntryDto());
        journalEntryService.create(getValidJournalEntryDto());
        journalEntryService.create(getValidJournalEntryDto());

        List<JournalEntryDto> userEntries = journalEntryService.findEntriesForUserId(USER_ID);

        assertThat(userEntries.size()).isEqualTo(3);
        assertThat(journalEntryRepository.count()).isEqualTo(3);
    }

    @Test
    void findById_successful() {
        JournalEntryDto dtoToCreate = getValidJournalEntryDto();
        JournalEntryDto createdDto = journalEntryService.create(dtoToCreate);

        long expectedId = 8L;
        JournalEntryDto foundDto = journalEntryService.findById(expectedId);

        assertThat(foundDto).isNotNull();
        assertThat(foundDto.getId()).isEqualTo(expectedId);
        assertThat(journalEntryRepository.count()).isEqualTo(1);
    }

    @Test
    void update_successful() {
        JournalEntryDto dtoToUpdate = getValidJournalEntryDto();
        journalEntryService.create(dtoToUpdate);

        String newContent = "new content";
        dtoToUpdate.setContent(newContent);

        JournalEntryDto updatedDto = journalEntryService.update(9L, dtoToUpdate);

        assertThat(updatedDto).isNotNull();
        assertThat(updatedDto.getUserId()).isEqualTo(dtoToUpdate.getUserId());
        assertThat(updatedDto.getTitle()).isEqualTo(dtoToUpdate.getTitle());
        assertThat(updatedDto.getContent()).isEqualTo(newContent);
        assertThat(journalEntryRepository.count()).isEqualTo(1);
    }

    @Test
    void delete_successful() {
        JournalEntryDto created = journalEntryService.create(getValidJournalEntryDto());
        journalEntryService.delete(created.getId());

        assertThat(journalEntryRepository.count()).isZero();
    }

    @Test
    void delete_unsuccessful() {
        journalEntryService.create(getValidJournalEntryDto());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            journalEntryService.delete(2L);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invalid journal entry id: 2");
        assertThat(journalEntryRepository.count()).isEqualTo(1);
    }

    private JournalEntryDto getValidJournalEntryDto() {
        return JournalEntryDto.builder()
                .userId(USER_ID)
                .title("some title")
                .content("some journal content")
                .build();
    }

    private JournalEntryDto getInvalidJournalEntryDto() {
        return JournalEntryDto.builder()
                .content("some journal content")
                .build();
    }

}