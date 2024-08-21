package com.ummati.ummati_core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDTO {

    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    @NotBlank(message = "Location is mandatory")
    private String location;
    //@MinCurrentDate
    private LocalDateTime date;
    private int duration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int maxParticipants;
    private String status;

    private Long organizationId;

    private List<Long> volunteerIds;
}
