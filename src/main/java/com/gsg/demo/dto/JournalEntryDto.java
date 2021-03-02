package com.gsg.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JournalEntryDto {
    private Long id;
    private String userId;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Content is mandatory")
    private String content;

    private LocalDateTime timestamp;
}
